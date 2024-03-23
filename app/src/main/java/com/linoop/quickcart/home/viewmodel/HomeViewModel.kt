package com.linoop.quickcart.home.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.home.state.HomePageDataState
import com.linoop.quickcart.home.usecase.GetProductsUseCase
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
    private val getProductsUseCase: com.linoop.quickcart.home.usecase.GetProductsUseCase,
) : ViewModel() {

    private val _queryErrorState: MutableState<Int?> = mutableStateOf(null)
    val queryErrorState: State<Int?> get() = _queryErrorState

    private val _productState: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(value = PagingData.empty())
    val productState: MutableStateFlow<PagingData<Product>> get() = _productState

    init {
        getProductsList()
    }
    fun getProductsList() = viewModelScope.launch {
        getProductsUseCase.invoke()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect { _productState.value = it }
    }
}