package com.sergiom.data.repository

import com.sergiom.data.net.response.BeerDataEntity
import com.sergiom.data.utils.Either

interface NetRepository {
    suspend fun getBeers(): Either<BeerDataEntity, String>
    suspend fun getSearchedBeers(query: String): Either<BeerDataEntity, String>
}