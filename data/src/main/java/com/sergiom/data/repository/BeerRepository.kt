package com.sergiom.data.repository

import com.sergiom.data.local.BeerItemsDao
import com.sergiom.data.mappers.Mapper
import com.sergiom.data.models.BeerItemDataBase
import com.sergiom.data.models.BeerModel
import com.sergiom.data.utils.performGetItem
import com.sergiom.data.utils.performGetItems
import javax.inject.Inject

class BeerRepository @Inject constructor(
    private val localDataSource: BeerItemsDao,
    private val mapper: Mapper<BeerModel, BeerItemDataBase>
) {
    suspend fun saveBeerToDatabase(beerItem: BeerModel) {
        localDataSource.insert(
            mapper.map(beerItem)
        )
    }

    fun getBeerFromDataBase(beerId: Int) = performGetItem( databaseQuery = { localDataSource.getBeer(beerId) })

    fun getBeersFromDataBase() = performGetItems( databaseQuery = { localDataSource.getBeers() })

    fun deleteAllBeers() = localDataSource.deleteAllBeers()

    fun deleteBeer(beerId: Int) = localDataSource.deleteBeer(beerId)
}