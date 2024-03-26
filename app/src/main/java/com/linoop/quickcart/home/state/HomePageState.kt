package com.linoop.quickcart.home.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import com.linoop.quickcart.main.model.GetProductsResponse
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.BaseState
import com.linoop.quickcart.utils.ViewState
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Home Page User State
 * @param productPagingItems product list pagination data
 * @param showInfo is a state to show App information
 * @param dataState is home page data state to hold product list
 */
data class HomePageUserState(
    val productPagingItems: MutableStateFlow<PagingData<Product>> = MutableStateFlow(value = PagingData.empty()),
    val showInfo: MutableState<Boolean> = mutableStateOf(false),
    val dataState: ViewState<HomePageDataState> = ViewState(HomePageDataState())
)

/**
 * Home page User event
 * @param getProductById trigger get a product by id, fetch the product form the server
 * @param getProductList trigger get product list download the list of product form the server
 */
data class HomePageUserEvent(
    val getProductById: (productId: Int) -> Unit = { _ -> },
    val getProductList: () -> Unit = { }
)

data class HomePageDataState(
    var getProductsResponse: GetProductsResponse = GetProductsResponse(),
    var apiState: ApiState = ApiState.Initial,
) : BaseState()

