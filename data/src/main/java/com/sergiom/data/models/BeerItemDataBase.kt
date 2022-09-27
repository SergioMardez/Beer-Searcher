package com.sergiom.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beer_items")
data class BeerItemDataBase(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "tagline") val tagline: String,
    @ColumnInfo(name = "first_brewed") val firstBrewed: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "abv") val abv: Double,
    @ColumnInfo(name = "ibu") val ibu: Double,
    @ColumnInfo(name = "target_fg") val targetFg: Double,
    @ColumnInfo(name = "target_og") val targetOg: Double,
    @ColumnInfo(name = "ebc") val ebc: Double,
    @ColumnInfo(name = "srm") val srm: Double,
    @ColumnInfo(name = "ph") val ph: Double,
    @ColumnInfo(name = "attenuation_level") val attenuationLevel: Double,
    @ColumnInfo(name = "ingredients") val ingredients: IngredientsDB,
    @ColumnInfo(name = "food_pairing") val foodPairing: List<String>,
    @ColumnInfo(name = "brewers_tips") val brewersTips: String,
    @ColumnInfo(name = "contributed_by") val contributedBy: String
)

data class IngredientsDB(
    @ColumnInfo(name = "malt") val malt: List<IngredientDB> = listOf(),
    @ColumnInfo(name = "hops") val hops: List<IngredientDB> = listOf(),
    @ColumnInfo(name = "yeast") val yeast: String = ""
)

data class IngredientDB(
    @ColumnInfo(name = "name") val name: String
)