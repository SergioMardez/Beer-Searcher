package com.sergiom.beersearcher

import com.google.gson.Gson
import com.sergiom.data.mappers.*
import com.sergiom.data.net.Api
import com.sergiom.data.net.response.BeerDataEntity
import com.sergiom.data.net.response.BeerEntity
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.utils.Either
import com.sergiom.data.utils.eitherSuccess
import com.sergiom.data.utils.onFailure
import com.sergiom.data.utils.onSuccess
import com.sergiom.domain.usecase.GetBeersUseCase
import com.sergiom.domain.usecase.GetBeersUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class GetBeersUseCaseTest {
    @Mock
    lateinit var mockNetRepo: NetRepository

    lateinit var mapper: BeerDataMapper
    lateinit var mapperBeer: BeerMapper
    lateinit var mapperIngredients: IngredientsMapper
    lateinit var mapperIngredient: IngredientMapper

    lateinit var useCaseToTest: GetBeersUseCase

    lateinit var mockBeers: BeerDataEntity

    @Mock
    lateinit var api: Api

    @Mock
    lateinit var mockResponse: Response<List<BeerEntity>>

    @Before
    fun setup() {
        mapperIngredient = IngredientMapper()
        mapperIngredients = IngredientsMapper(mapperIngredient)
        mapperBeer = BeerMapper(mapperIngredients)
        mapper = BeerDataMapper(mapperBeer)
        mockBeers = Gson().fromJson(beers, BeerDataEntity::class.java)
        useCaseToTest = GetBeersUseCaseImpl(mockNetRepo, mapper)
    }

    @Test
    fun `get net data and success`()  {
        runBlocking {
            Mockito.`when`(mockNetRepo.getBeers()).thenReturn(eitherSuccess(mockBeers))
            assert(useCaseToTest() is Either.Success)
            useCaseToTest.invoke().onSuccess {
                assert(it.beers.isNotEmpty())
                assert(it.beers.size == 3)
                assert(it.beers.first().name == "Buzz")
            }
        }
    }

    @Test
    fun `get data and fail`()  {
        runBlocking {
            Mockito.`when`(mockResponse.isSuccessful).thenReturn(false)
            Mockito.`when`(api.getBeers()).thenReturn(mockResponse)
            assert(useCaseToTest() is Either.Failure)
            useCaseToTest.invoke().onFailure {
                assert(it.isNotEmpty())
            }
        }
    }

}