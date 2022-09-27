package com.sergiom.beersearcher.ui.beersearchview

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiom.data.models.BeerDataModel
import com.sergiom.data.models.BeerModel
import com.sergiom.data.utils.onFailure
import com.sergiom.data.utils.onSuccess
import com.sergiom.domain.usecase.GetBeerSearchUseCase
import com.sergiom.domain.usecase.GetBeersUseCase
import com.sergiom.domain.usecase.SaveBeerToDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerSearchViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase,
    private val getBeerSearchUseCase: GetBeerSearchUseCase,
    private val saveBeerToDataBaseUseCase: SaveBeerToDataBaseUseCase
): ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state.asStateFlow()

    init {
        Handler(Looper.getMainLooper()).postDelayed({
            getBeers()
        }, 1000) //To see logo image like a splash
    }

    fun getBeers() {
        viewModelScope.launch {
            val result = getBeersUseCase.invoke()
            result.onSuccess { items ->
                _state.update {
                    it.copy(loading = false, beers = items)
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(loading = false, error = error)
                }
            }
        }
    }

    fun getBeerQuery(query: String) {
        viewModelScope.launch {
            val result = getBeerSearchUseCase.invoke(query)
            result.onSuccess { items ->
                _state.update {
                    it.copy(loading = false, beers = items)
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(loading = false, error = error)
                }
            }
        }
    }

    fun saveBeerToDataBase(item: BeerModel) {
        viewModelScope.launch {
            saveBeerToDataBaseUseCase.invoke(item)
        }
    }

    data class State(
        var loading: Boolean = true,
        var beers: BeerDataModel? = null,
        var error: String? = null
    )

}