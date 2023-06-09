package services

import com.github.tototoshi.csv._
import java.io.File
import scala.collection.MapView
import model.{Person, Manager, Chef, Location, Price, Restaurant, Currency}

// Represents the data for a restaurant
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
    // Overrides the toString method to provide a formatted string representation of the data
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

object DataReader {
  /**
   * Reads restaurant data from a CSV file.
   *
   * @param filePath The path to the CSV file.
   * @return A list of RestaurantData objects representing the data from the file.
   */
  def readDataFromFile(filePath: String): List[RestaurantData] = {
    try {
      val reader = CSVReader.open(new File(filePath))
      val header = reader.readNext()
      val rows = reader.all()

      // Maps each row in the CSV file to a RestaurantData object
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
      restaurantData
    } catch {
      case ex: Exception =>
        println("Error ocurred during data reading: " + ex.getMessage)
        List.empty[RestaurantData]
    }
  }
}