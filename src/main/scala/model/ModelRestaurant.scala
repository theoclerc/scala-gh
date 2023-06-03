package model

// Represents a person with a name
case class Person(name: String)

// ManagerTrait trait extends Person and represents a manager
sealed trait ManagerTrait extends Person

// ChefTrait trait extends Person and represents a chef
sealed trait ChefTrait extends Person

// Manager class with a name, extends Person and implements ManagerTrait
class Manager(name: String) extends Person(name) with ManagerTrait

// Chef class with a name, extends Person and implements ChefTrait
class Chef(name: String) extends Person(name) with ChefTrait

// Represents a location with a city, country, latitude, and longitude
case class Location(city: String, country: String, latitude: Double, longitude: Double)

// Represents a price with an amount and a currency
case class Price(menu: Int, currency: Currency)

// Enum Currency representing the possible currencies
enum Currency {
    case BRL, EUR, DKK, THB, PEN, CHF, HKD, USD, CLP, RUB, SGD, GBP, JPY, MXN, SEK, ZAR
}

// Represents a restaurant with a ranking, name, location, stars, chef, manager, website, price, and description
case class Restaurant(ranking: Int, name: String, location: Location, stars: String, chef: Chef, manager: Manager, website: String, price: Price, description: String)
