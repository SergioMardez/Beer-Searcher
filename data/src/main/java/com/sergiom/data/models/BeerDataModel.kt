package com.sergiom.data.models

data class BeerDataModel(
    val beers: List<BeerModel> = listOf()
)

data class BeerModel(
    val id: Int,
    val name: String,
    val tagline: String,
    val firstBrewed: String,
    val description: String,
    val imageUrl: String,
    val abv: Double,
    val ibu: Double,
    val targetFg: Double,
    val targetOg: Double,
    val ebc: Double,
    val srm: Double,
    val ph: Double,
    val attenuationLevel: Double,
    val ingredients: IngredientsModel,
    val foodPairing: List<String>,
    val brewersTips: String,
    val contributedBy: String
)

data class IngredientsModel(
    val malt: List<IngredientModel> = listOf(),
    val hops: List<IngredientModel> = listOf(),
    val yeast: String = ""
)

data class IngredientModel(
    val name: String
)
