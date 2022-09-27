package com.sergiom.domain.usecase

import com.sergiom.data.models.BeerModel
import com.sergiom.data.repository.BeerRepository
import javax.inject.Inject

interface SaveBeerToDataBaseUseCase {
    suspend operator fun invoke(beerModel: BeerModel)
}

class SaveBeerToDataBaseUseCaseImpl @Inject constructor(
    private val repository: BeerRepository
): SaveBeerToDataBaseUseCase {
    override suspend fun invoke(beerModel: BeerModel) {
        repository.saveBeerToDatabase(beerModel)
    }
}