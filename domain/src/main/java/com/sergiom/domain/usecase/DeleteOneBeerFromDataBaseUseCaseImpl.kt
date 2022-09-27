package com.sergiom.domain.usecase

import com.sergiom.data.repository.BeerRepository
import javax.inject.Inject

interface DeleteOneBeerFromDataBaseUseCase {
    suspend operator fun invoke(beerId: Int)
}

class DeleteOneBeerFromDataBaseUseCaseImpl @Inject constructor(
    private val repository: BeerRepository
): DeleteOneBeerFromDataBaseUseCase {
    override suspend fun invoke(beerId: Int) {
        repository.deleteBeer(beerId)
    }
}