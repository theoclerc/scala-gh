# SCALA PROJECT - BEST RESTAURANTS

This project provides services for processing restaurant data. It includes functionalities such as filtering highly rated restaurants, calculating average menu prices, finding the restaurant with the highest ranking, grouping restaurants by country, and counting the number of restaurants in each city.

## Contributors

- Bétrisey Julienne
- Clerc Théo

## Prerequisites

- Scala 2.13.x
- `com.github.tototoshi.csv` library

## Getting Started

1. Open the project Folder into your Scala IDE (we used Visual Studio Code).

2. Place the restaurant data CSV file (`02-50BestRestaurants.csv`) in the `data` directory.

## Usage

The project contains the following Scala files:

1. **Main.scala**: Contains the main entry point of the program. It displays a menu and performs actions based on user input.

2. **ModelRestaurant.scala**: Defines the model classes for representing a person, manager, chef, location, price, currency, and restaurant.

3. **DataReader.scala**: Provides a utility to read restaurant data from a CSV file.

4. **ServicesRestaurant.scala**: Provides services for processing restaurant data, including filtering highly rated restaurants, calculating average stars by country, finding the restaurant with David as manager, grouping restaurants by country, and counting the number of restaurants in each city.

To run the program, execute the `Main` object in your Scala IDE. Follow the on-screen instructions to choose an action from the menu.




