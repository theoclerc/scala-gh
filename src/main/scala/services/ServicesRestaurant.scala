package services

import model.{Chef, Location, Manager, Price, Currency}
import scala.collection.MapView

/**
 * This object provides various services related to restaurant data.
 */
object ServicesRestaurant {
  
  /**
   * Filters the given sequence of RestaurantData to include only highly rated restaurants.
   *
   * @param data The sequence of RestaurantData.
   * @return A new sequence of RestaurantData containing only highly rated restaurants.
   */
  def filterHighlyRatedRestaurants(data: Seq[RestaurantData]): Seq[RestaurantData] = {
    try {
      data.filter(_.stars.exists(_ > 2))
    } catch {
      case ex: Exception =>
        println("Error occurred during filtering highly rated restaurants: " + ex.getMessage)
        Seq.empty[RestaurantData]
    }
  }

  /**
   * Calculates the average stars by country for the given sequence of RestaurantData.
   *
   * @param data The sequence of RestaurantData.
   * @return A map where the keys are country names and the values are the average stars.
   */
  def calculateAverageStarsByCountry(data: Seq[RestaurantData]): Map[String, Double] = {
    try {
      val starsByCountry = data.flatMap { restaurant =>
        restaurant.stars.flatMap { stars =>
          if (stars > 0) Some((restaurant.location.country, stars.toDouble)) else None
        }
      }
      val starsSumByCountry = starsByCountry.groupBy(_._1).mapValues(_.map(_._2).sum)
      val restaurantCountByCountry = data.groupBy(_.location.country).mapValues(_.size)
      val averageStarsByCountry = starsSumByCountry.map { case (country, starsSum) =>
        val count = restaurantCountByCountry.getOrElse(country, 0)
        val averageStars = if (count > 0) starsSum / count.toDouble else 0.0
        (country, averageStars)
      }
      averageStarsByCountry.toMap
    } catch {
      case ex: Exception =>
        println("Error occurred during average stars calculation: " + ex.getMessage)
        Map.empty[String, Double]
    }
  }

   /**
   * Finds all restaurants where the manager's name contains "David" from the given sequence of RestaurantData.
   *
   * @param data The sequence of RestaurantData.
   * @return A sequence of RestaurantData representing the restaurants with managers containing "David",
   *         or an empty sequence if no matches are found.
   */
  def findRestaurantsWithDavidAsManager(data: Seq[RestaurantData]): Seq[RestaurantData] = {
    try {
      data.filter(_.manager.name.toLowerCase.contains("david"))
    } catch {
      case ex: Exception =>
        println("Error occurred during finding restaurants with David as manager: " + ex.getMessage)
        Seq.empty[RestaurantData]
    }
  }

  /**
   * Groups the given sequence of RestaurantData by country.
   *
   * @param data The sequence of RestaurantData.
   * @return A map where the keys are country names and the values are sequences of RestaurantData
   *         belonging to each country.
   */
  def groupRestaurantsByCountry(data: Seq[RestaurantData]): Map[String, Seq[RestaurantData]] = {
    try {
      data.groupBy(_.location.country)
    } catch {
      case ex: Exception =>
        println("Error occurred during grouping restaurants by country: " + ex.getMessage)
        Map.empty[String, Seq[RestaurantData]]
    }
  }

  /**
   * Counts the number of restaurants in each city from the given sequence of RestaurantData.
   *
   * @param data The sequence of RestaurantData.
   * @return A MapView where the keys are city names and the values are the counts of restaurants
   *         in each city.
   */
  def countRestaurantsByCity(data: Seq[RestaurantData]): MapView[String, Int] = {
    try {
      data.groupBy(_.location.city).mapValues(_.size)
    } catch {
      case ex: Exception =>
        println("Error occurred during counting restaurants by city: " + ex.getMessage)
        Map.empty[String, Int].view
    }
  }
}