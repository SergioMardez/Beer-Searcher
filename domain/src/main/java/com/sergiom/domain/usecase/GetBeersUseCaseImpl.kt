package com.sergiom.domain.usecase

import com.sergiom.data.mappers.Mapper
import com.sergiom.data.models.BeerDataModel
import com.sergiom.data.net.response.BeerDataEntity
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.utils.*
import javax.inject.Inject

interface GetBeersUseCase {
    suspend operator fun invoke(): Either<BeerDataModel, String>
}

class GetBeersUseCaseImpl @Inject constructor(
    private val repository: NetRepository,
    private val mapper: @JvmSuppressWildcards Mapper<BeerDataEntity, BeerDataModel>
): GetBeersUseCase {
    override suspend fun invoke(): Either<BeerDataModel, String> {
        val result = repository.getBeers()
        result.onSuccess {
            if(it.beers.isNullOrEmpty()) return eitherFailure("Empty error")
            return eitherSuccess(mapper.map(it))
        }.onFailure {
            return eitherFailure(it)
        }
        return eitherFailure("Undefined Error")
    }
}