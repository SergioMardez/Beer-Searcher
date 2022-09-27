package com.sergiom.beersearcher

import com.sergiom.data.extensions.errorsHandle
import com.sergiom.data.net.response.BeerDataEntity
import com.sergiom.data.net.response.BeerEntity
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.utils.eitherSuccess
import com.sergiom.data.utils.onFailure
import com.sergiom.data.utils.onSuccess
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class DataSourceTest {

    @Mock
    lateinit var mockNetRepo: NetRepository

    @Mock
    lateinit var mockResponse: Response<List<BeerEntity>>

    @Mock
    lateinit var mockBeerEntity: List<BeerEntity>

    @Test
    fun `get data and success`() {
        runBlocking {
            Mockito.`when`(mockResponse.isSuccessful).thenReturn(true)
            Mockito.`when`(mockResponse.body()).thenReturn(mockBeerEntity)
            Mockito.`when`(mockNetRepo.getBeers()).thenAnswer {
                eitherSuccess(BeerDataEntity(beers = mockResponse.errorsHandle()))
            }
            mockNetRepo.getBeers().onSuccess {
                assert(it == BeerDataEntity(beers = mockResponse.body()))
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            mockNetRepo.getBeers().onSuccess {
                assert(it.equals(null))
            }.onFailure {
                assert(it.isNotEmpty())
            }
        }
    }

}