package com.sergiom.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.sergiom.data.models.IngredientsDB

class DataTypeConverter {

    companion object {
        var gson = Gson()

        @TypeConverter
        @JvmStatic
        fun ingredientsToString(ingredients: IngredientsDB): String =
            gson.toJson(ingredients)

        @TypeConverter
        @JvmStatic
        fun stringToIngredients(ingredients: String): IngredientsDB =
            try {
                gson.fromJson(ingredients, IngredientsDB::class.java)
            } catch (e: JsonSyntaxException) {
                null
            } ?: IngredientsDB()

        @TypeConverter
        @JvmStatic
        fun foodPairingToString(foodPairing: List<String>): String =
            gson.toJson(foodPairing)

        @TypeConverter
        @JvmStatic
        fun stringToFoodPairing(foodPairing: String): List<String> =
            try {
                gson.fromJson(foodPairing, Array<String>::class.java).asList()
            } catch (e: JsonSyntaxException) {
                null
            } ?: listOf()

    }

}