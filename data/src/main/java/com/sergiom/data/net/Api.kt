package com.sergiom.data.net

import com.sergiom.data.net.response.BeerEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @Headers( "Accept: application/json",
        "Content-type:application/json")
    @GET("beers")
    suspend fun getBeers(): Response<List<BeerEntity>>

    @Headers( "Accept: application/json",
        "Content-type:application/json")
    @GET("beers")
    suspend fun getSearchedBeers(@Query("beer_name") query: String):Response<List<BeerEntity>>
}