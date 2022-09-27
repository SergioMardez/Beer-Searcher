package com.sergiom.data.repositoryimpl

import com.sergiom.data.extensions.errorsHandle
import com.sergiom.data.net.RestClient
import com.sergiom.data.net.response.BeerDataEntity
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.utils.Either
import com.sergiom.data.utils.eitherFailure
import com.sergiom.data.utils.eitherSuccess
import javax.inject.Inject

class NetRepositoryImpl @Inject constructor(
    private val restClient: RestClient
): NetRepository {

    override suspend fun getBeers(): Either<BeerDataEntity, String> {
        try {
            val result = restClient.getRemoteCaller().getBeers()
            result.let {
                val beers = BeerDataEntity(beers = result.errorsHandle())
                return eitherSuccess(beers)
            }
        } catch (e: Exception) {
            return eitherFailure(e.toString())
        }
    }

    override suspend fun getSearchedBeers(query: String): Either<BeerDataEntity, String> {
        try {
            val result = restClient.getRemoteCaller().getSearchedBeers(query)
            result.let {
                val beers = BeerDataEntity(beers = result.errorsHandle())
                return eitherSuccess(beers)
            }
        } catch (e: Exception) {
            return eitherFailure(e.toString())
        }
    }

}