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
    data.filter(_.stars.exists(_ > 2))
  }

  /**
   * Calculates the average menu price in Euros for the given sequence of RestaurantData.
   *
   * @param data The sequence of RestaurantData.
   * @return The average menu price in Euros.
   */
  def calculateAverageMenuPrice(data: Seq[RestaurantData]): Double = {
    data.map(_.price.menu).sum.toDouble / data.length
  }

  /**
   * Finds the restaurant with the highest ranking from the given sequence of RestaurantData.
   *
   * @param data The sequence of RestaurantData.
   * @return An optional RestaurantData representing the restaurant with the highest ranking,
   *         or None if the sequence is empty.
   */
  def findRestaurantWithHighestRanking(data: Seq[RestaurantData]): Option[RestaurantData] = {
    data.maxByOption(_.ranking)
  }

  /**
   * Groups the given sequence of RestaurantData by country.
   *
   * @param data The sequence of RestaurantData.
   * @return A map where the keys are country names and the values are sequences of RestaurantData
   *         belonging to each country.
   */
  def groupRestaurantsByCountry(data: Seq[RestaurantData]): Map[String, Seq[RestaurantData]] = {
    data.groupBy(_.location.country)
  }

  /**
   * Counts the number of restaurants in each city from the given sequence of RestaurantData.
   *
   * @param data The sequence of RestaurantData.
   * @return A MapView where the keys are city names and the values are the counts of restaurants
   *         in each city.
   */
  def countRestaurantsByCity(data: Seq[RestaurantData]): MapView[String, Int] = {
    data.groupBy(_.location.city).mapValues(_.size)
  }
}
