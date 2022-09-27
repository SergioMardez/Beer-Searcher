package com.sergiom.domain.usecase

import com.sergiom.data.mappers.Mapper
import com.sergiom.data.models.BeerDataModel
import com.sergiom.data.net.response.BeerDataEntity
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.utils.*
import javax.inject.Inject

interface GetBeerSearchUseCase {
    suspend operator fun invoke(query: String): Either<BeerDataModel, String>
}

class GetBeerSearchUseCaseImpl @Inject constructor(
    private val repository: NetRepository,
    private val mapper: @JvmSuppressWildcards Mapper<BeerDataEntity, BeerDataModel>
): GetBeerSearchUseCase {
    override suspend fun invoke(query: String): Either<BeerDataModel, String> {
        val result = repository.getSearchedBeers(query)
        result.onSuccess {
            return eitherSuccess(mapper.map(it))
        }.onFailure {
            return eitherFailure(it)
        }
        return eitherFailure("Undefined Error")
    }
}