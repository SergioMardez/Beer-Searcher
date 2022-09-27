package com.sergiom.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sergiom.data.models.BeerItemDataBase

@Dao
interface BeerItemsDao {

    @Query("SELECT * FROM beer_items")
    fun getBeers() : LiveData<List<BeerItemDataBase>>

    @Query("SELECT * FROM beer_items WHERE id = :id")
    fun getBeer(id: Int): LiveData<BeerItemDataBase>

    @Query("DELETE FROM beer_items")
    fun deleteAllBeers()

    @Query("DELETE FROM beer_items WHERE id = :id")
    fun deleteBeer(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: BeerItemDataBase)

}