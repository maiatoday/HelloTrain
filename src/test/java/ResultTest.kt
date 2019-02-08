import com.google.common.truth.Truth.*
import org.junit.Test

class ResultTest {

    val happyIngredients = Success<Ingredients, ErrorCode>(listOf(Apple(), Cherry(), Banana()))
    val happyFruitPie = FruitPie(colour= Apple().colour, weight = 71)
    val successHappyFruitPie = SuccessFood(happyFruitPie)
    val evilIngredients = Success<Ingredients, ErrorCode>(listOf(Durian(), Cherry()))
    val baarf = Fail<Food, ErrorCode>(ErrorCode.STINKY_MESS)
    val boom = Fail<Ingredients, ErrorCode>(ErrorCode.EXPLOSION)
    val dietFood = Fail<Food, ErrorCode>(ErrorCode.ZERO_CALORIES)

    @Test
    fun `test any fail returns a fail`() {
        val test: Result<Answer, ErrorCode> = Fail(ErrorCode.GENERAL_ERROR)
        val mapped = test.map { 1 + 1 }
        assertThat(mapped is Fail).named("mapped value")
        assertWithMessage("mapped fail is fail").that(mapped is Fail)
    }

    @Test
    fun `food fight`() {
        val result = makeDish(Success(listOf()))
        assertThat(result is Success)

        val result2 = makeDish(boom)
        assertThat(result2 is Fail)

        val result3 = makeDish(evilIngredients)
        assertThat(result3 is Fail)
        assertThat((result3 as Fail).value).isEqualTo(ErrorCode.STINKY_MESS)
        assertThat(result3).isEqualTo(baarf)

        val result4 = makeDish(happyIngredients)
        assertThat(result4 is Success)
        assertThat((result4 as Success).value.weight).isEqualTo(71)
        assertThat(result4).isEqualTo(successHappyFruitPie)

        makeDish(happyIngredients).map { println(it.colour) }

        happyIngredients.map { it.forEach { println(it.colour) } }
        val anotherlist = happyIngredients.map { it.map {it.colour } }
        boom.map { it.forEach { println(it.colour) } }
        val colourList = happyIngredients.value.map { it.colour}
        assertThat(colourList).containsAllOf("Red", "Yellow", "Green")

    }

    @Test
    fun `fun with food`() {

        val successApple = Success<Food, ErrorCode>(Apple())
        assertThat((successApple as Success).value.colour).isEqualTo("Green")

        val thing2 = successApple.map {Banana(weight = it.weight)}
        assertThat((thing2 as Success).value is Banana)
        assertThat((thing2 as Success).value.weight).isEqualTo((successApple as Success).value.weight)

        val successAppleRepeatable = successApple.flatMap {Success<Ingredients, ErrorCode>(listOf(it, it, it))}
        assertThat((successAppleRepeatable as Success).value.size).isEqualTo(3)

        val thingFood = makeDish(successAppleRepeatable)
        assertThat(thingFood is Success)
        assertThat((thingFood as Success).value.weight).isEqualTo(150)

        val failToBanana = baarf.orElse(Banana())
        assertThat(failToBanana is Banana)

        val originalApple = successApple.orElse(Cherry())
        assertThat(originalApple is Apple)

        val explosionToIngredients = boom.orElse(listOf(Cherry(), Durian()))
        assertThat(explosionToIngredients.size).isEqualTo(2)
        assertThat(explosionToIngredients).containsExactly(Cherry(), Durian())

        val mysteryDessert = makeDish(Success(explosionToIngredients))
        assertThat((mysteryDessert as Fail).value).isEqualTo(ErrorCode.STINKY_MESS)
        assertThat(mysteryDessert).isEqualTo(baarf)

        val bigBang = boom.map {ErrorCode.STINKY_MESS}

        val ping = makeDish(evilIngredients).orElse(Cherry())
        assertThat(ping).isEqualTo(Cherry())

        val pingPing = makeDish(happyIngredients).orElse(Cherry())
        assertThat(pingPing).isEqualTo(happyFruitPie)

        assertThat(bigBang is Fail)
        assertThat(baarf is Fail)
    }

    @Test
    fun `Pie fight`() {
        val pie1 = makePie(SuccessFood(Apple()))
        assertThat(pie1 is Success)
        assertThat((pie1 as SuccessFood).value is ApplePie)

        val oops = makePie(makeDish(evilIngredients))
        assertThat(oops is FailFood)
        assertThat(oops).isEqualTo(baarf)

        val secretToSuccess:SuccessDish = Success(listOf(Cherry(), Banana()))
        val yummy = makePie(makeDish(secretToSuccess))
        assertThat(yummy is FailFood)
        assertThat(yummy).isEqualTo(boom)

        val mysterySuccessAttemp:SuccessDish = Success(listOf())
        val whatCouldItBe = makePie(makeDish(mysterySuccessAttemp))
        assertThat(whatCouldItBe is FailFood)
        assertThat(whatCouldItBe).isEqualTo(dietFood)

        val fruitList = listOf<SuccessFood>(Success(Apple()), Success(Banana()), Success(Cherry()), Success(Durian()))
        val foodList = fruitList.map { makePie(it) }
        assertThat(foodList.size == 4)

    }

}



