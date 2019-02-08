open class Food(open val colour: String, open val weight: Int)
data class Apple(override val colour: String = "Green", val acidity: Int = 3, override val weight: Int = 50) :
    Food(colour, weight)

data class Cherry(override val colour: String = "Red", val acidity: Int = 6, override val weight: Int = 1) :
    Food(colour, weight)

data class Banana(override val colour: String = "Yellow", val acidity: Int = 8, override val weight: Int = 20) :
    Food(colour, weight)

data class Durian(override val colour: String = "Brown", val acidity: Int = 7, override val weight: Int = 10) :
    Food(colour, weight)

data class AppleMouse(override val colour: String = "Brown", val acidity: Int = 4, override val weight: Int = 55) :
    Food(colour, weight)

data class FruitPie(override val colour: String = "Beige", val acidity: Int = 7, override val weight: Int = 100) :
    Food(colour, weight)

typealias Ingredients=List<Food>