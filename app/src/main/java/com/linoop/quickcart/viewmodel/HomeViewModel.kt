package com.linoop.quickcart.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.state.HomePageDataState
import com.linoop.quickcart.usecase.GetProductsUseCase
import com.linoop.quickcart.usecase.PagProductUseCase
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.Resource
import com.linoop.quickcart.utils.StateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetProductsUseCase,
    private val pagProductUseCase: PagProductUseCase,
) : ViewModel() {

    private val _queryErrorState: MutableState<Int?> = mutableStateOf(null)
    val queryErrorState: State<Int?> = _queryErrorState

    private val _apiDataState = mutableStateOf(StateHolder(HomePageDataState()))
    val apiDataState: State<StateHolder<HomePageDataState>> = _apiDataState

    private val _productState: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(value = PagingData.empty())
    val productState: MutableStateFlow<PagingData<Product>> get() = _productState

    fun getProductsList() = viewModelScope.launch {
        pagProductUseCase.invoke()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect { _productState.value = it }
    }


    fun getProducts() = viewModelScope.launch {
        useCase.invoke().collect { response ->
            when (response) {
                is Resource.Loading -> {
                    _apiDataState.value = apiDataState.value
                        .copy(HomePageDataState().also { it.apiState = ApiState.Loading })
                }

                is Resource.Success -> {
                    val state = HomePageDataState().apply {
                        apiState = ApiState.Success
                        message = response.message.toString()
                        getProductsResponse = response.data ?: GetProductsResponse()
                    }
                    _apiDataState.value = apiDataState.value.copy(value = state)
                }

                is Resource.Error -> {
                    val state = HomePageDataState().apply {
                        apiState = ApiState.Error
                        message = response.message.toString()
                    }
                    _apiDataState.value = apiDataState.value.copy(value = state)
                }
            }
        }
    }

}