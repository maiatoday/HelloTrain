fun makeDish(ingredients: Result<List<Food>, ErrorCode>): Result<Food, ErrorCode> =
    ingredients.flatMap {
        when {
            it.contains(Durian()) -> Fail<Food, ErrorCode>(ErrorCode.STINKY_MESS)
            it.isEmpty() -> Fail<Food, ErrorCode>(ErrorCode.ZERO_CALORIES)
            else -> Success<Food, ErrorCode>(
                FruitPie(it.first().colour, 7, it.sumBy {item -> item.weight}))

        }
    }
