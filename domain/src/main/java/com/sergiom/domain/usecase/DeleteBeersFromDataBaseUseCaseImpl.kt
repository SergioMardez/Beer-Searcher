package com.sergiom.domain.usecase

import com.sergiom.data.repository.BeerRepository
import javax.inject.Inject

interface DeleteBeersFromDataBaseUseCase {
    suspend operator fun invoke()
}

class DeleteBeersFromDataBaseUseCaseImpl @Inject constructor(
    private val repository: BeerRepository
): DeleteBeersFromDataBaseUseCase {
    override suspend fun invoke() {
        repository.deleteAllBeers()
    }
}