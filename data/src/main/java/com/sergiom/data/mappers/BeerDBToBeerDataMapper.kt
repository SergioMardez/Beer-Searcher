package com.sergiom.data.mappers

import com.sergiom.data.models.*
import javax.inject.Inject

class BeerDBToBeerDataMapper @Inject constructor(
    private val mapperIngredients: @JvmSuppressWildcards Mapper<IngredientsDB, IngredientsModel>
): Mapper<BeerItemDataBase, BeerModel> {
    override fun map(input: BeerItemDataBase): BeerModel =
        BeerModel(
            id = input.id,
            name = input.name,
            tagline = input.tagline,
            firstBrewed = input.firstBrewed,
            description = input.description,
            imageUrl = input.imageUrl,
            abv = input.abv,
            ibu = input.ibu,
            targetFg = input.targetFg,
            targetOg = input.targetOg,
            ebc = input.ebc,
            srm = input.srm,
            ph = input.ph,
            attenuationLevel = input.attenuationLevel,
            ingredients = mapperIngredients.map(input.ingredients),
            foodPairing = input.foodPairing,
            brewersTips = input.brewersTips,
            contributedBy = input.contributedBy
        )
}

class IngredientsDBToDataMapper @Inject constructor(
    private val mapperIngredient: @JvmSuppressWildcards Mapper<IngredientDB, IngredientModel>
): Mapper<IngredientsDB, IngredientsModel> {
    override fun map(input: IngredientsDB): IngredientsModel =
        IngredientsModel(
            malt = input.malt.map { mapperIngredient.map(it) },
            hops = input.hops.map { mapperIngredient.map(it) },
            yeast = input.yeast
        )
}

class IngredientDBToDataMapper @Inject constructor(): Mapper<IngredientDB, IngredientModel> {
    override fun map(input: IngredientDB): IngredientModel =
        IngredientModel(
            name = input.name ?: ""
        )
}