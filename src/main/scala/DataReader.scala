import com.github.tototoshi.csv._
import java.io.File
import scala.io.StdIn

// Define a case class to represent the data from each row of the CSV
case class RestaurantData(
  ranking: Int,
  restaurant: String,
  city: String,
  country: String,
  lat: Double,
  lon: Double,
  stars: Option[Int], // Use an Option[Int] to handle the 'NA' case
  chef: String,
  manager: String,
  website: String,
  menu: Int,
  currency: String,
  description: String
) {
  override def toString: String = {
    s"""
       |Ranking: $ranking
       |Restaurant: $restaurant
       |City: $city
       |Country: $country
       |Latitude: $lat
       |Longitude: $lon
       |Stars: ${stars.getOrElse("N/A")}
       |Chef: $chef
       |Manager: $manager
       |Website: $website
       |Menu: $menu $currency
       |Description: $description
       |""".stripMargin
  }
}

object DataExploration {
  def main(args: Array[String]): Unit = {
    // Create an instance of CSVReader and read the CSV file
    val reader = CSVReader.open(new File("data/02-50BestRestaurants.csv"))

    // Read the header row
    val header = reader.readNext()

    // Read the remaining rows and map them to RestaurantData instances
    val rows = reader.all()
    val restaurantData = rows.map(row => {
      val data = row.map(_.trim)
      val stars = data(6) match {
        case "NA" => None // Handle the 'NA' case by using None
        case s => Some(s.toInt)
      }
      RestaurantData(
        data(0).toInt,
        data(1),
        data(2),
        data(3),
        data(4).toDouble,
        data(5).toDouble,
        stars,
        data(7),
        data(8),
        data(9),
        data(10).toInt,
        data(11),
        data(12)
      )
    })

    // Close the reader
    reader.close()

    var choice = 0

    // Menu loop
    while (choice != 5) {
      println("Menu:")
      println("1. Filter restaurants with more than 2 stars")
      println("2. Calculate the average menu price in Euros")
      println("3. Find the restaurant with the highest ranking")
      println("4. Group restaurants by country")
      println("5. Count the number of restaurants in each city")
      println("Enter the number of the action you want to perform (1-5):")
      choice = StdIn.readInt()

      choice match {
        case 1 =>
          val highlyRatedRestaurants = restaurantData.filter(_.stars.exists(_ > 2))
          println("Highly rated restaurants:")
          highlyRatedRestaurants.foreach(println)
        case 2 =>
          val averagePrice = restaurantData.map(_.menu).sum.toDouble / restaurantData.length
          println(s"Average menu price in Euros: $averagePrice")
        case 3 =>
          val highestRankingRestaurant = restaurantData.maxBy(_.ranking)
          println("Restaurant with the highest ranking:")
          println(highestRankingRestaurant)
        case 4 =>
          val restaurantsByCountry = restaurantData.groupBy(_.country)
          println("Restaurants grouped by country:")
          restaurantsByCountry.foreach { case (country, restaurants) =>
            println(s"Country: $country")
            restaurants.foreach(println)
          }
        case 5 =>
          val restaurantCountByCity = restaurantData.groupBy(_.city).mapValues(_.size)
          println("Restaurant count by city:")
          restaurantCountByCity.foreach { case (city, count) =>
            println(s"$city: $count")
          }
        case _ =>
          println("Invalid choice. Please enter a number between 1 and 5.")
      }
      println()
    }
  }
}
