package main

import com.github.tototoshi.csv._
import java.io.File
import scala.collection.MapView
import model.{Person, Manager, Chef, Location, Price, Restaurant, Currency}
import services.ServicesRestaurant
import services.DataReader


object Main {
  def main(args: Array[String]): Unit = {
    try {
      val filePath = "data/02-50BestRestaurants.csv"
      // Reads the restaurant data from the specified file
      val restaurantData = DataReader.readDataFromFile(filePath)

      // Displays a menu and performs actions based on user input
      var choice = ""
      while (choice != "Exit") {
        println("Menu:")
        println("1. Filter restaurants with more than 2 stars")
        println("2. Calculate the average stars by country")
        println("3. Find the restaurant whose manager's name is David")
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
            try {
              val averageStarsByCountry = ServicesRestaurant.calculateAverageStarsByCountry(restaurantData)
              println("Average stars by country:")
              averageStarsByCountry.foreach { case (country, averageStars) =>
                println(s"$country: $averageStars")
              }
            } catch {
              case ex: Exception =>
                println("Error occurred: " + ex.getMessage)
            }
          case "3" =>
            val restaurantsWithDavidAsManager = ServicesRestaurant.findRestaurantsWithDavidAsManager(restaurantData)
            println("Restaurants with David as manager:")
            restaurantsWithDavidAsManager.foreach(println)
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
    } catch {
      case ex: Exception =>
      println("Error occurred: " + ex.getMessage)
    }
  }
}
