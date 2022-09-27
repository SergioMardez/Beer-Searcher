package com.sergiom.data.mappers

import com.sergiom.data.models.*
import javax.inject.Inject

class BeerDataBaseMapper @Inject constructor(
    private val mapperIngredients: @JvmSuppressWildcards Mapper<IngredientsModel, IngredientsDB>
): Mapper<BeerModel, BeerItemDataBase> {
    override fun map(input: BeerModel): BeerItemDataBase =
        BeerItemDataBase(
            id = input.id,
            name = input.name,
            tagline = input.tagline,
            firstBrewed = input.firstBrewed,
            description = input.description,
            imageUrl = input.imageUrl,
            abv = input.abv,
            ibu = 0.0,
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

class IngredientsDataBaseMapper @Inject constructor(
    private val mapperIngredient: @JvmSuppressWildcards Mapper<IngredientModel, IngredientDB>
): Mapper<IngredientsModel, IngredientsDB> {
    override fun map(input: IngredientsModel): IngredientsDB =
        IngredientsDB(
            malt = input.malt.map { mapperIngredient.map(it) },
            hops = input.hops.map { mapperIngredient.map(it) },
            yeast = input.yeast
        )
}

class IngredientDataBaseMapper @Inject constructor(): Mapper<IngredientModel, IngredientDB> {
    override fun map(input: IngredientModel): IngredientDB =
        IngredientDB(
            name = input.name
        )
}