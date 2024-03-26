package com.linoop.quickcart.home.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.BaseState
import com.linoop.quickcart.utils.ViewState
import kotlinx.coroutines.flow.MutableStateFlow

data class HomePageUserState(
    val productPagingItems: MutableStateFlow<PagingData<Product>> = MutableStateFlow(value = PagingData.empty()),
    val showInfo: MutableState<Boolean> = mutableStateOf(false),
    val dataState: ViewState<HomePageDataState> = ViewState(HomePageDataState())
)

data class HomePageUserEvent(
    val getProductById: (productId: Int) -> Unit = { _ -> },
    val getProductList: () -> Unit = { }
)

data class HomePageDataState(
    var getProductsResponse: GetProductsResponse = GetProductsResponse(),
    var apiState: ApiState = ApiState.Initial,
) : BaseState()

