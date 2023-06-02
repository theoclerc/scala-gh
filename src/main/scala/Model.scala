
// Représente une personne avec un nom
case class Person(name: String)

// Trait ManagerTrait étend Person, représente un manager
sealed trait ManagerTrait extends Person

// Trait ChefTrait étend Person, représente un chef
sealed trait ChefTrait extends Person

// Classe Manager avec un nom, étend Person et implémente ManagerTrait
class Manager(name: String) extends Person(name) with ManagerTrait

// Classe Chef avec un nom, étend Person et implémente ChefTrait
class Chef(name: String) extends Person(name) with ChefTrait


// Représente une localisation avec une ville, un pays, une latitude et une longitude
case class Location(city: String, country: String, latitude: Double, longitude: Double)

// Représente un prix avec un montant et une devise
case class Price(menu: Int, currency: Currency)

// Enum Currency représentant les différentes devises possibles
enum Currency {
    case BRL, EUR, DKK, THB, PEN, CHF, HKD, USD, CLP, RUB, SGD, GBP, JPY, MXN, SEK, ZAR
}

// Représente un restaurant avec un classement, un nom, une localisation, un nombre d'étoiles, un chef, un manager, un site web, un prix et une description
case class Restaurant(ranking: Int, name: String, location: Location, stars: String, chef: Chef, manager: Manager, website: String, price: Price, description: String)
