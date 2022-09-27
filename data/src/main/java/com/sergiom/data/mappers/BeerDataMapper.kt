package com.sergiom.data.mappers

import com.sergiom.data.models.BeerDataModel
import com.sergiom.data.models.BeerModel
import com.sergiom.data.models.IngredientModel
import com.sergiom.data.models.IngredientsModel
import com.sergiom.data.net.response.BeerDataEntity
import com.sergiom.data.net.response.BeerEntity
import com.sergiom.data.net.response.IngredientEntity
import com.sergiom.data.net.response.IngredientsEntity
import javax.inject.Inject

class BeerDataMapper @Inject constructor(
    private val mapper: @JvmSuppressWildcards Mapper<BeerEntity, BeerModel>
) : Mapper<BeerDataEntity, BeerDataModel> {
    override fun map(input: BeerDataEntity): BeerDataModel =
        BeerDataModel(
            beers = input.beers?.map { mapper.map(it) } ?: listOf()
        )
}

class BeerMapper @Inject constructor(
    private val mapperIngredients: @JvmSuppressWildcards Mapper<IngredientsEntity?, IngredientsModel>
): Mapper<BeerEntity, BeerModel> {
    override fun map(input: BeerEntity): BeerModel =
        BeerModel(
            id = input.id ?: 0,
            name = input.name ?: "",
            tagline = input.tagline ?: "",
            firstBrewed = input.firstBrewed ?: "",
            description = input.description ?: "",
            imageUrl = input.imageUrl ?: "",
            abv = input.abv ?: 0.0,
            ibu = input.ibu ?: 0.0,
            targetFg = input.targetFg ?: 0.0,
            targetOg = input.targetOg ?: 0.0,
            ebc = input.ebc ?: 0.0,
            srm = input.srm ?: 0.0,
            ph = input.ph ?: 0.0,
            attenuationLevel = input.attenuationLevel ?: 0.0,
            ingredients = mapperIngredients.map(input.ingredients),
            foodPairing = input.foodPairing ?: listOf(),
            brewersTips = input.brewersTips ?: "",
            contributedBy = input.contributedBy ?: ""
        )
}

class IngredientsMapper @Inject constructor(
    private val mapperIngredient: @JvmSuppressWildcards Mapper<IngredientEntity, IngredientModel>
): Mapper<IngredientsEntity?, IngredientsModel> {
    override fun map(input: IngredientsEntity?): IngredientsModel =
        IngredientsModel(
            malt = input?.malt?.map { mapperIngredient.map(it) } ?: listOf(),
            hops = input?.hops?.map { mapperIngredient.map(it) } ?: listOf(),
            yeast = input?.yeast ?: ""
        )
}

class IngredientMapper: Mapper<IngredientEntity, IngredientModel> {
    override fun map(input: IngredientEntity): IngredientModel =
        IngredientModel(
            name = input.name ?: ""
        )
}