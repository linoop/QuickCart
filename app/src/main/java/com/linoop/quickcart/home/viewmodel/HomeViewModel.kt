package com.linoop.quickcart.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.linoop.quickcart.home.usecase.GetProductsUseCase
import com.linoop.quickcart.main.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    private val _productState: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(value = PagingData.empty())
    val productState: MutableStateFlow<PagingData<Product>> get() = _productState

    init {
        getProductsList()
    }

    /**
     * Get product list download the list of product from the server
     * it update the product list state and update the paginated data
     */
    fun getProductsList() = viewModelScope.launch {
        getProductsUseCase.invoke()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect { _productState.value = it }
    }
}