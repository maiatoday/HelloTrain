fun makeDish(ingredients: ResultIngredients): ResultFood =
    ingredients.flatMap {
        when {
            it.contains(Durian()) -> Fail<Food, ErrorCode>(ErrorCode.STINKY_MESS)
            it.isEmpty() -> Fail<Food, ErrorCode>(ErrorCode.ZERO_CALORIES)
            else -> Success<Food, ErrorCode>(
                FruitPie(it.first().colour, 7, it.sumBy { item -> item.weight })
            )

        }
    }

fun makePie(ingredient: ResultFood): ResultFood =
    ingredient.flatMap {
        when (it) {
            is FruitPie ->
                if (it.colour == "Red")
                    FailFood(ErrorCode.EXPLOSION)
                else SuccessFood(it)
            is Durian -> FailFood(ErrorCode.STINKY_MESS)
            is Apple -> SuccessFood(ApplePie())
            is Cherry -> SuccessFood(CherryPie())
            is Banana -> SuccessFood(BananaMousse())
            else -> SuccessFood(FruitPie())
        }
    }
