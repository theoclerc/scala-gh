package main

import com.github.tototoshi.csv._
import java.io.File
import scala.collection.MapView
import model.{Person, Manager, Chef, Location, Price, Restaurant, Currency}
import services.ServicesRestaurant
import services.DataReader


object Main {
  def main(args: Array[String]): Unit = {
    
    val filePath = "data/02-50BestRestaurants.csv"
    // Reads the restaurant data from the specified file
    val restaurantData = DataReader.readDataFromFile(filePath)

    // Displays a menu and performs actions based on user input
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
      choice = scala.io.StdIn.readLine()

      choice match {
        case "1" =>
          val highlyRatedRestaurants = ServicesRestaurant.filterHighlyRatedRestaurants(restaurantData)
          println("Highly rated restaurants:")
          highlyRatedRestaurants.foreach(println)
        case "2" =>
          val averagePrice = ServicesRestaurant.calculateAverageMenuPrice(restaurantData)
          println(s"Average menu price in Euros: $averagePrice")
        case "3" =>
          val highestRankingRestaurant = ServicesRestaurant.findRestaurantWithHighestRanking(restaurantData)
          highestRankingRestaurant.foreach { restaurant =>
            println("Restaurant with the highest ranking:")
            println(restaurant)
          }
        case "4" =>
          val restaurantsByCountry = ServicesRestaurant.groupRestaurantsByCountry(restaurantData)
          println("Restaurants grouped by country:")
          restaurantsByCountry.foreach { case (country, restaurants) =>
            println(s"Country: $country")
            restaurants.foreach(println)
          }
        case "5" =>
          val restaurantCountByCity = ServicesRestaurant.countRestaurantsByCity(restaurantData)
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
