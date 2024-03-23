package com.linoop.quickcart.product.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.product.state.ProductPageDataState
import com.linoop.quickcart.product.usecase.GetProductByIdUseCase
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.InputValidator
import com.linoop.quickcart.utils.Resource
import com.linoop.quickcart.utils.StateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductById: GetProductByIdUseCase
) : ViewModel() {

    private val _apiDataState = mutableStateOf(StateHolder(ProductPageDataState()))
    val apiDataState: State<StateHolder<ProductPageDataState>> get() = _apiDataState
    fun getProductByID(productId: Int?) = viewModelScope.launch {
        val errorID = InputValidator.validateProductId(productId)
        if (errorID == null) getProductById.invoke(productId!!).collect { response ->
            when (response) {
                is Resource.Loading -> {
                    _apiDataState.value = apiDataState.value
                        .copy(ProductPageDataState().also { it.apiState = ApiState.Loading })
                }

                is Resource.Success -> {
                    val state = ProductPageDataState().apply {
                        apiState = ApiState.Success
                        message = response.message.toString()
                        product = response.data ?: Product()
                    }
                    _apiDataState.value = apiDataState.value.copy(value = state)
                }

                is Resource.Error -> {
                    val state = ProductPageDataState().apply {
                        apiState = ApiState.Error
                        message = response.message.toString()
                    }
                    _apiDataState.value = apiDataState.value.copy(value = state)
                }
            }
        }
    }
}