package com.linoop.quickcart.product.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.product.state.AddToCartDataState
import com.linoop.quickcart.product.state.ProductPageDataState
import com.linoop.quickcart.product.usecase.AddToCartUseCase
import com.linoop.quickcart.product.usecase.GetProductByIdUseCase
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.InputValidator
import com.linoop.quickcart.utils.Resource
import com.linoop.quickcart.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductById: GetProductByIdUseCase,
    private val addToCartUseCase: AddToCartUseCase,
) : ViewModel() {

    private val _productDataState = mutableStateOf(ViewState(ProductPageDataState()))
    val productDataState: State<ViewState<ProductPageDataState>> get() = _productDataState

    private val _addToCartDataState = mutableStateOf(ViewState(AddToCartDataState()))
    val addToCartDataState: State<ViewState<AddToCartDataState>> get() = _addToCartDataState

    /**
     * Get Product by ID is a function to fetch the Product details from the server
     * @param productId is a [Int] parameter that received from the home page
     */
    fun getProductByID(productId: Int?) = viewModelScope.launch {
        val errorID = InputValidator.validateProductId(productId)
        if (errorID == null) getProductById.invoke(productId!!).collect { response ->
            when (response) {
                is Resource.Loading -> {
                    _productDataState.value = productDataState.value
                        .copy(ProductPageDataState().also { it.apiState = ApiState.Loading })
                }

                is Resource.Success -> {
                    val state = ProductPageDataState().apply {
                        apiState = ApiState.Success
                        message = response.message.toString()
                        product = response.data ?: Product()
                    }
                    _productDataState.value = productDataState.value.copy(value = state)
                }

                is Resource.Error -> {
                    val state = ProductPageDataState().apply {
                        apiState = ApiState.Error
                        message = response.message.toString()
                    }
                    _productDataState.value = productDataState.value.copy(value = state)
                }
            }
        }
    }

    /**
     * Add to Cart function to save a product to Database
     * @param product is type of [Product] which the user wish to add the the cart
     */
    fun addToCart(product: Product) = viewModelScope.launch {
        addToCartUseCase.invoke(product).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    val state = AddToCartDataState(apiState = ApiState.Loading)
                    _addToCartDataState.value = addToCartDataState.value.copy(value = state)
                }

                is Resource.Success -> {
                    val state = AddToCartDataState(apiState = ApiState.Success)
                        .apply { id = resource.data ?: 0 }
                    _addToCartDataState.value = addToCartDataState.value.copy(value = state)
                }

                is Resource.Error -> {
                    val state = AddToCartDataState(apiState = ApiState.Error)
                    _addToCartDataState.value = addToCartDataState.value.copy(value = state)
                }
            }
        }
    }
}