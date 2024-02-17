package io.github.serpro69.kfaker.app.cli

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.provider.Address
import io.github.serpro69.kfaker.travel.provider.Airport
import io.github.serpro69.kfaker.games.provider.Dota
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlin.reflect.KClass

class IntrospectorTest : DescribeSpec() {
    private val faker = Faker()

    init {
        describe("Introspector class") {
            val introspector = Introspector(faker)

            context("list all Faker providers") {
                val providers = introspector.providers
                    .map { it.getter.returnType.classifier as KClass<*> }
                    .map { it.simpleName }

                it("should contain all providers") {
                    val expectedProviders = listOf(
                        "Address",
                        "Adjective",
                        "Airport",
                        "Ancient",
                        "Animal",
                        "App",
                        "Appliance",
                        "AquaTeenHungerForce",
                        "Archer",
                        "Artist",
                        "Australia",
                        "Avatar",
                        "BackToTheFuture",
                        "Bank",
                        "Barcode",
                        "Basketball",
                        "Beer",
                        "Bible",
                        "BigBangTheory",
                        "Bird",
                        "Blood",
                        "BojackHorseman",
                        "Book",
                        "BossaNova",
                        "BreakingBad",
                        "BrooklynNineNine",
                        "Buffy",
                        "Business",
                        "Camera",
                        "Cannabis",
                        "Cat",
                        "Chess",
                        "Chiquito",
                        "ChuckNorris",
                        "ClashOfClans",
                        "Code",
                        "Coffee",
                        "Coin",
                        "Color",
                        "Commerce",
                        "Community",
                        "Company",
                        "Computer",
                        "Conan",
                        "Construction",
                        "Control",
                        "Cosmere",
                        "CowboyBebop",
                        "Crossfit",
                        "CryptographyProvider",
                        "CryptoCoin",
                        "CultureSeries",
                        "Currency",
                        "CurrencySymbol",
                        "DcComics",
                        "Demographic",
                        "Departed",
                        "Dessert",
                        "Device",
                        "DnD",
                        "Dog",
                        "Doraemon",
                        "Dota",
                        "DrWho",
                        "DragonBall",
                        "DrivingLicense",
                        "Drone",
                        "DumbAndDumber",
                        "Dune",
                        "ESport",
                        "Educator",
                        "ElderScrolls",
                        "ElectricalComponents",
                        "Emotion",
                        "Fallout",
                        "FamilyGuy",
                        "File",
                        "FinalFantasyXIV",
                        "FinalSpace",
                        "Finance",
                        "FmaBrotherhood",
                        "Food",
                        "Football",
                        "FreshPriceOfBelAir",
                        "Friends",
                        "FunnyName",
                        "Futurama",
                        "Game",
                        "GameOfThrones",
                        "Gender",
                        "GhostBusters",
                        "GratefulDead",
                        "GreekPhilosophers",
                        "Hacker",
                        "Hackers",
                        "HalfLife",
                        "HarryPotter",
                        "Heroes",
                        "HeroesOfTheStorm",
                        "HeyArnold",
                        "Hipster",
                        "HitchhikersGuideToTheGalaxy",
                        "Hobbit",
                        "Hobby",
                        "Horse",
                        "House",
                        "HowIMetYourMother",
                        "HowToTrainYourDragon",
                        "IdNumber",
                        "IndustrySegments",
                        "Internet",
                        "JackHandey",
                        "Job",
                        "KPop",
                        "KamenRider",
                        "LeagueOfLegends",
                        "Lebowski",
                        "LordOfTheRings",
                        "Lorem",
                        "Lovecraft",
                        "Markdown",
                        "Marketing",
                        "Measurement",
                        "MichaelScott",
                        "Military",
                        "Minecraft",
                        "MitchHedberg",
                        "Money",
                        "Mountain",
                        "Mountaineering",
                        "Movie",
                        "Music",
                        "Myst",
                        "Name",
                        "Naruto",
                        "Nation",
                        "NatoPhoneticAlphabet",
                        "NewGirl",
                        "OnePiece",
                        "Opera",
                        "Overwatch",
                        "ParksAndRec",
                        "PearlJam",
                        "Phish",
                        "PhoneNumber",
                        "Pokemon",
                        "Prince",
                        "PrincessBride",
                        "ProgrammingLanguage",
                        "Quote",
                        "Rajnikanth",
                        "Relationship",
                        "Restaurant",
                        "RickAndMorty",
                        "RockBand",
                        "Room",
                        "Rupaul",
                        "Rush",
                        "Science",
                        "Seinfeld",
                        "Separator",
                        "Shakespeare",
                        "Show",
                        "SiliconValley",
                        "Simpsons",
                        "SlackEmoji",
                        "SmashingPumpkins",
                        "SonicTheHedgehog",
                        "SouthPark",
                        "Space",
                        "Spongebob",
                        "Sport",
                        "StarTrek",
                        "StarWars",
                        "Stargate",
                        "StrangerThings",
                        "StreetFighter",
                        "Stripe",
                        "StudioGhibli",
                        "Subscription",
                        "Suits",
                        "SuperMario",
                        "SuperSmashBros",
                        "Superhero",
                        "Supernatural",
                        "SwordArtOnline",
                        "Tarkov",
                        "Tea",
                        "Team",
                        "TheExpanse",
                        "TheITCrowd",
                        "TheKingkillerChronicle",
                        "TheOffice",
                        "TheRoom",
                        "TheThickOfIt",
                        "Tolkien",
                        "Touhou",
                        "TrainStation",
                        "Tron",
                        "TwinPeaks",
                        "UmphreysMcgee",
                        "University",
                        "VForVendetta",
                        "Vehicle",
                        "VentureBros",
                        "Verbs",
                        "Volleyball",
                        "WarhammerFantasy",
                        "Witcher",
                        "WorldCup",
                        "WorldOfWarcraft",
                        "Yoda",
                        "Zelda"
                    )
                    providers.toList() shouldContainExactly expectedProviders
                }
            }

            context("list available functions for each provider") {
                val providerData = introspector.providerData

                it("should contain all providers") {
                    val providers = providerData.map { it.key.name }
                    val expectedProviders = listOf(
                        "address",
                        "adjective",
                        "airport",
                        "ancient",
                        "animal",
                        "app",
                        "appliance",
                        "aquaTeenHungerForce",
                        "archer",
                        "artist",
                        "australia",
                        "avatar",
                        "backToTheFuture",
                        "bank",
                        "barcode",
                        "basketball",
                        "beer",
                        "bible",
                        "bigBangTheory",
                        "bird",
                        "blood",
                        "bojackHorseman",
                        "book",
                        "bossaNova",
                        "breakingBad",
                        "brooklynNineNine",
                        "buffy",
                        "business",
                        "camera",
                        "cannabis",
                        "cat",
                        "chess",
                        "chiquito",
                        "chuckNorris",
                        "clashOfClans",
                        "code",
                        "coffee",
                        "coin",
                        "color",
                        "commerce",
                        "community",
                        "company",
                        "computer",
                        "conan",
                        "construction",
                        "control",
                        "cosmere",
                        "cowboyBebop",
                        "crossfit",
                        "crypto",
                        "cryptoCoin",
                        "cultureSeries",
                        "currency",
                        "currencySymbol",
                        "dcComics",
                        "demographic",
                        "departed",
                        "dessert",
                        "device",
                        "dnd",
                        "dog",
                        "doraemon",
                        "dota",
                        "drWho",
                        "dragonBall",
                        "drivingLicense",
                        "drone",
                        "dumbAndDumber",
                        "dune",
                        "eSport",
                        "educator",
                        "elderScrolls",
                        "electricalComponents",
                        "emotion",
                        "fallout",
                        "familyGuy",
                        "file",
                        "finalFantasyXIV",
                        "finalSpace",
                        "finance",
                        "fmaBrotherhood",
                        "food",
                        "football",
                        "freshPriceOfBelAir",
                        "friends",
                        "funnyName",
                        "futurama",
                        "game",
                        "gameOfThrones",
                        "gender",
                        "ghostBusters",
                        "gratefulDead",
                        "greekPhilosophers",
                        "hacker",
                        "hackers",
                        "halfLife",
                        "harryPotter",
                        "heroes",
                        "heroesOfTheStorm",
                        "heyArnold",
                        "hipster",
                        "hitchhikersGuideToTheGalaxy",
                        "hobbit",
                        "hobby",
                        "horse",
                        "house",
                        "howIMetYourMother",
                        "howToTrainYourDragon",
                        "idNumber",
                        "industrySegments",
                        "internet",
                        "jackHandey",
                        "job",
                        "kPop",
                        "kamenRider",
                        "leagueOfLegends",
                        "lebowski",
                        "lordOfTheRings",
                        "lorem",
                        "lovecraft",
                        "markdown",
                        "marketing",
                        "measurement",
                        "michaelScott",
                        "military",
                        "minecraft",
                        "mitchHedberg",
                        "money",
                        "mountain",
                        "mountaineering",
                        "movie",
                        "music",
                        "myst",
                        "name",
                        "naruto",
                        "nation",
                        "natoPhoneticAlphabet",
                        "newGirl",
                        "onePiece",
                        "opera",
                        "overwatch",
                        "parksAndRec",
                        "pearlJam",
                        "phish",
                        "phoneNumber",
                        "pokemon",
                        "prince",
                        "princessBride",
                        "programmingLanguage",
                        "quote",
                        "rajnikanth",
                        "relationship",
                        "restaurant",
                        "rickAndMorty",
                        "rockBand",
                        "room",
                        "rupaul",
                        "rush",
                        "science",
                        "seinfeld",
                        "separator",
                        "shakespeare",
                        "show",
                        "siliconValley",
                        "simpsons",
                        "slackEmoji",
                        "smashingPumpkins",
                        "sonicTheHedgehog",
                        "southPark",
                        "space",
                        "spongebob",
                        "sport",
                        "starTrek",
                        "starWars",
                        "stargate",
                        "strangerThings",
                        "streetFighter",
                        "stripe",
                        "studioGhibli",
                        "subscription",
                        "suits",
                        "superMario",
                        "superSmashBros",
                        "superhero",
                        "supernatural",
                        "swordArtOnline",
                        "tarkov",
                        "tea",
                        "team",
                        "theExpanse",
                        "theITCrowd",
                        "theKingkillerChronicle",
                        "theOffice",
                        "theRoom",
                        "theThickOfIt",
                        "tolkien",
                        "touhou",
                        "trainStation",
                        "tron",
                        "twinPeaks",
                        "umphreysMcgee",
                        "university",
                        "vForVendetta",
                        "vehicle",
                        "ventureBros",
                        "verbs",
                        "volleyball",
                        "warhammerFantasy",
                        "witcher",
                        "worldCup",
                        "worldOfWarcraft",
                        "yoda",
                        "zelda"
                    )

                    providers shouldContainExactly expectedProviders
                }

                it("should contain all functions of the provider") {
                    val addressFunctions = providerData.entries.first { (provider, _) ->
                        (provider.returnType.classifier as KClass<*>) == Address::class
                    }.value.first.map { it.name }

                    val expectedFunctions = listOf(
                        "buildingNumber",
                        "city",
                        "cityPrefix",
                        "citySuffix",
                        "cityWithState",
                        "community",
                        "communityPrefix",
                        "communitySuffix",
                        "country",
                        "countryByCode",
                        "countryByName",
                        "countryCode",
                        "countryCodeLong",
                        "defaultCountry",
                        "fullAddress",
                        "mailbox",
                        "postcode",
                        "postcodeByState",
                        "secondaryAddress",
                        "state",
                        "stateAbbr",
                        "streetAddress",
                        "streetName",
                        "streetSuffix",
                        "timeZone"
                    )

                    addressFunctions.toList() shouldContainExactly expectedFunctions
                }

                it("should not contain deprecated functions") {
                    val addressFunctions = providerData.entries.first { (provider, _) ->
                        (provider.returnType.classifier as KClass<*>) == Dota::class
                    }.value.first.map { it.name }

                    val expectedFunctions = listOf("building", "hero", "item", "player", "team")

                    addressFunctions.toList() shouldContainExactly expectedFunctions
                }

                it("should contain all sub-providers of the provider") {
                    val subProviders = providerData.entries.first { (provider, _) ->
                        (provider.returnType.classifier as KClass<*>) == Airport::class
                    }.value.second

                    subProviders.map { it.key.name } shouldContainExactly listOf("europeanUnion", "unitedStates")
                }
            }
        }
    }
}
