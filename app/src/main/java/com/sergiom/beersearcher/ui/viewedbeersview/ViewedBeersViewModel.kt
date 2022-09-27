package com.sergiom.beersearcher.ui.viewedbeersview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiom.data.mappers.BeerDBToBeerDataMapper
import com.sergiom.data.models.BeerDataModel
import com.sergiom.data.models.BeerItemDataBase
import com.sergiom.data.models.BeerModel
import com.sergiom.domain.usecase.DeleteBeersFromDataBaseUseCase
import com.sergiom.domain.usecase.GetBeersFromDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewedBeersViewModel @Inject constructor(
    private val getBeersFromDataBaseUseCase: GetBeersFromDataBaseUseCase,
    private val deleteBeersFromDataBaseUseCase: DeleteBeersFromDataBaseUseCase,
    private val mapper: BeerDBToBeerDataMapper
): ViewModel() {

    private val _state = MutableStateFlow(State(beers = getBeersFromDataBaseUseCase.invoke()))
    val state: StateFlow<State> get() = _state.asStateFlow()

    fun mapBeers(beers: List<BeerItemDataBase>): BeerDataModel {
        val mappedBeers = mutableListOf<BeerModel>()
        beers.forEach {
            mappedBeers.add(mapper.map(it))
        }
        return BeerDataModel(beers = mappedBeers)
    }

    fun deleteAllBeers() {
        viewModelScope.launch(Dispatchers.IO)  {
            deleteBeersFromDataBaseUseCase.invoke()
        }
    }

    data class State(
        var beers: LiveData<List<BeerItemDataBase>>
    )

}