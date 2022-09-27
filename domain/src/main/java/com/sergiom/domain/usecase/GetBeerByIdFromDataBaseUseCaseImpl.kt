package com.sergiom.domain.usecase

import androidx.lifecycle.LiveData
import com.sergiom.data.models.BeerItemDataBase
import com.sergiom.data.repository.BeerRepository
import javax.inject.Inject

interface GetBeerByIdFromDataBaseUseCase {
    fun invoke(beerId: Int): LiveData<BeerItemDataBase>
}

class GetBeerByIdFromDataBaseUseCaseImpl  @Inject constructor(
    private val repository: BeerRepository
): GetBeerByIdFromDataBaseUseCase {
    override fun invoke(beerId: Int): LiveData<BeerItemDataBase> =
        repository.getBeerFromDataBase(beerId)
}