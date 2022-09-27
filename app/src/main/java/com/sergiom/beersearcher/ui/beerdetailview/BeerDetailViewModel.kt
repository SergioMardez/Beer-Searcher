package com.sergiom.beersearcher.ui.beerdetailview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiom.data.models.BeerItemDataBase
import com.sergiom.domain.usecase.DeleteOneBeerFromDataBaseUseCase
import com.sergiom.domain.usecase.GetBeerByIdFromDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val getBeerByIdFromDataBaseUseCase: GetBeerByIdFromDataBaseUseCase,
    private val deleteOneBeerFromDataBaseUseCase: DeleteOneBeerFromDataBaseUseCase
): ViewModel() {

    private lateinit var _state: MutableStateFlow<State>
    val state: StateFlow<State> get() = _state.asStateFlow()

    fun getBeer(beerId: Int) {
        _state = MutableStateFlow(State(beer = getBeerByIdFromDataBaseUseCase.invoke(beerId)))
    }

    fun deleteBeer(beerId: Int) {
        viewModelScope.launch(Dispatchers.IO)  {
            deleteOneBeerFromDataBaseUseCase.invoke(beerId)
        }
    }

    data class State(
        var beer: LiveData<BeerItemDataBase>
    )

}