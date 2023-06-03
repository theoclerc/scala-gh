package services

import com.github.tototoshi.csv._
import java.io.File
import scala.io.StdIn
import model.{Person, Manager, Chef, Location, Price, Restaurant}
import model.Currency

case class RestaurantData(
  ranking: Int,
  restaurant: String,
  location: Location,
  stars: Option[Int],
  chef: Chef,
  manager: Manager,
  website: String,
  price: Price,
  description: String
) {
  override def toString: String = {
    s"""
       |Ranking: $ranking
       |Restaurant: $restaurant
       |Location: $location
       |Stars: ${stars.getOrElse("N/A")}
       |Chef: $chef
       |Manager: $manager
       |Website: $website
       |Price: $price
       |Description: $description
       |""".stripMargin
  }
}
object DataExploration {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("data/02-50BestRestaurants.csv"))
    val header = reader.readNext()
    val rows = reader.all()
    val restaurantData = rows.map(row => {
      val data = row.map(_.trim)
      val stars = data(6) match {
        case "NA" => None
        case s => Some(s.toInt)
      }
      RestaurantData(
        data(0).toInt,
        data(1),
        Location(data(2), data(3), data(4).toDouble, data(5).toDouble),
        stars,
        Chef(data(7)),
        Manager(data(8)),
        data(9),
        Price(data(10).toInt, Currency.valueOf(data(11))),
        data(12)
      )
    })
    reader.close()

    var choice = ""

    while (choice != "Exit") {
      println("Menu:")
      println("1. Filter restaurants with more than 2 stars")
      println("2. Calculate the average menu price in Euros")
      println("3. Find the restaurant with the highest ranking")
      println("4. Group restaurants by country")
      println("5. Count the number of restaurants in each city")
      println("Exit. Exit the program")
      println("Enter the number or 'Exit' to choose the action you want to perform:")
      choice = StdIn.readLine()

      choice match {
        case "1" =>
          val highlyRatedRestaurants = restaurantData.filter(_.stars.exists(_ > 2))
          println("Highly rated restaurants:")
          highlyRatedRestaurants.foreach(println)
        case "2" =>
          val averagePrice = restaurantData.map(_.price.menu).sum.toDouble / restaurantData.length
          println(s"Average menu price in Euros: $averagePrice")
        case "3" =>
          val highestRankingRestaurant = restaurantData.maxBy(_.ranking)
          println("Restaurant with the highest ranking:")
          println(highestRankingRestaurant)
        case "4" =>
          val restaurantsByCountry = restaurantData.groupBy(_.location.country)
          println("Restaurants grouped by country:")
          restaurantsByCountry.foreach { case (country, restaurants) =>
            println(s"Country: $country")
            restaurants.foreach(println)
          }
        case "5" =>
          val restaurantCountByCity = restaurantData.groupBy(_.location.city).mapValues(_.size)
          println("Restaurant count by city:")
          restaurantCountByCity.foreach { case (city, count) =>
            println(s"$city: $count")
          }
        case "Exit" =>
          println("Exiting the program...")
        case _ =>
          println("Invalid choice. Please enter a number between 1 and 5.")
      }
      println()
    }
  }
}
