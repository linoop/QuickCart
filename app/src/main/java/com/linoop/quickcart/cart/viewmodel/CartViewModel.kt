package com.linoop.quickcart.cart.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linoop.quickcart.cart.state.DeleteItemDataState
import com.linoop.quickcart.cart.state.OpenCartDataState
import com.linoop.quickcart.cart.usecase.DeleteFormCartUseCase
import com.linoop.quickcart.cart.usecase.OpenCartUseCase
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.Resource
import com.linoop.quickcart.utils.StateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val openCartUseCase: OpenCartUseCase,
    private val deleteFormCartUseCase: DeleteFormCartUseCase
) : ViewModel() {
    private val _openCartDataState = mutableStateOf(StateHolder(OpenCartDataState()))
    val openCartDataState: State<StateHolder<OpenCartDataState>> get() = _openCartDataState

    private val _deleteItemDataState = mutableStateOf(StateHolder(DeleteItemDataState()))
    val deleteItemDataState: State<StateHolder<DeleteItemDataState>> get() = _deleteItemDataState
    fun openCart() = viewModelScope.launch(Dispatchers.IO) {
        openCartUseCase.invoke().collect { response ->
            when (response) {
                is Resource.Loading -> {
                    val state = OpenCartDataState(apiState = ApiState.Loading)
                    _openCartDataState.value = openCartDataState.value.copy(value = state)
                }

                is Resource.Success -> {
                    val state = OpenCartDataState().apply {
                        apiState = ApiState.Success
                        products = response.data ?: listOf()
                    }
                    _openCartDataState.value = openCartDataState.value.copy(value = state)
                }

                is Resource.Error -> {
                    val state = OpenCartDataState(apiState = ApiState.Error)
                    _openCartDataState.value = openCartDataState.value.copy(value = state)
                }
            }
        }
    }

    fun deleteFormCart(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        deleteFormCartUseCase.invoke(product).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    val state = DeleteItemDataState(apiState = ApiState.Loading)
                    _deleteItemDataState.value = deleteItemDataState.value.copy(value = state)
                }

                is Resource.Success -> {
                    val state = DeleteItemDataState(apiState = ApiState.Success)
                    _deleteItemDataState.value = deleteItemDataState.value.copy(value = state)
                }

                is Resource.Error -> {
                    val state = DeleteItemDataState(apiState = ApiState.Error)
                    _deleteItemDataState.value = deleteItemDataState.value.copy(value = state)
                }
            }
        }
    }
}