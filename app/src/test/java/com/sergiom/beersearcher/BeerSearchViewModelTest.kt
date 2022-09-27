package com.sergiom.beersearcher

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.sergiom.beersearcher.ui.beersearchview.BeerSearchViewModel
import com.sergiom.data.models.BeerDataModel
import com.sergiom.data.repository.BeerRepository
import com.sergiom.data.utils.eitherSuccess
import com.sergiom.domain.usecase.GetBeerSearchUseCase
import com.sergiom.domain.usecase.GetBeersUseCase
import com.sergiom.domain.usecase.SaveBeerToDataBaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BeerSearchViewModelTest {

    @Mock
    lateinit var getBeersUseCase: GetBeersUseCase

    @Mock
    lateinit var getBeerSearchUseCase: GetBeerSearchUseCase

    @Mock
    lateinit var saveBeerToDataBaseUseCase: SaveBeerToDataBaseUseCase

    lateinit var beerSearchViewModel: BeerSearchViewModel
    lateinit var mockBeerData: BeerDataModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val dispatcher = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        mockBeerData = Gson().fromJson(beers, BeerDataModel::class.java)
        beerSearchViewModel = BeerSearchViewModel(
            getBeersUseCase = getBeersUseCase,
            getBeerSearchUseCase = getBeerSearchUseCase,
            saveBeerToDataBaseUseCase = saveBeerToDataBaseUseCase
        )
    }


    @Test
    fun `init viewModel test`() {
        runBlocking {
            assert(beerSearchViewModel.state.value.loading)
            assert(beerSearchViewModel.state.value.error.isNullOrEmpty())
            assert(beerSearchViewModel.state.value.beers?.beers.isNullOrEmpty())
        }
    }

    @Test
    fun `get beers data and success`()  {
        runBlocking {
            Mockito.`when`(getBeersUseCase.invoke()).thenReturn(
                eitherSuccess(mockBeerData)
            )
            beerSearchViewModel.getBeers()
            assert(beerSearchViewModel.state.value.loading.not())
            assert(beerSearchViewModel.state.value.error.isNullOrEmpty())
            assert(beerSearchViewModel.state.value.beers!!.beers.size == 3)
            assert(beerSearchViewModel.state.value.beers!!.beers.last().name == "Berliner Weisse With Yuzu - B-Sides")
        }
    }

    @Test
    fun `get beers search data and success`()  {
        runBlocking {
            val response = BeerDataModel(beers = listOf(mockBeerData.beers.first()))
            Mockito.`when`(getBeerSearchUseCase.invoke("buzz")).thenReturn(
                eitherSuccess(response)
            )
            beerSearchViewModel.getBeerQuery("buzz")
            assert(beerSearchViewModel.state.value.loading.not())
            assert(beerSearchViewModel.state.value.error.isNullOrEmpty())
            assert(beerSearchViewModel.state.value.beers!!.beers.size == 1)
            assert(beerSearchViewModel.state.value.beers!!.beers.first().name == "Buzz")
        }
    }

}