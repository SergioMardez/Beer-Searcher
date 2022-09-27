package com.sergiom.domain.usecase

import androidx.lifecycle.LiveData
import com.sergiom.data.models.BeerItemDataBase
import com.sergiom.data.repository.BeerRepository
import javax.inject.Inject

interface GetBeersFromDataBaseUseCase {
    fun invoke(): LiveData<List<BeerItemDataBase>>
}

class GetBeersFromDataBaseUseCaseImpl @Inject constructor(
    private val repository: BeerRepository
): GetBeersFromDataBaseUseCase {
    override fun invoke(): LiveData<List<BeerItemDataBase>> =
        repository.getBeersFromDataBase()
}