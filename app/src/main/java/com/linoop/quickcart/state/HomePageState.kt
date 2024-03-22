package com.linoop.quickcart.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.BaseState
import com.linoop.quickcart.utils.Constants.EMPTY
import com.linoop.quickcart.utils.StateHolder
import kotlinx.coroutines.flow.MutableStateFlow

@Stable
data class HomePageInitialValue(
    var query: String = EMPTY,
)

data class HomePageUserState(
    val initialValue: HomePageInitialValue = HomePageInitialValue(),
    val productPagingItems: MutableStateFlow<PagingData<Product>> = MutableStateFlow(value = PagingData.empty()),
    val queryError: State<Int?> = mutableStateOf(null),
    val showInfo: MutableState<Boolean> = mutableStateOf(false),
    val dataState: StateHolder<HomePageDataState> = StateHolder(HomePageDataState())
)

data class HomePageUserEvent(
    val getProduct: () -> Unit = { },
    val getProductList: () -> Unit = { }
)

data class HomePageDataState(
    var getProductsResponse: GetProductsResponse = GetProductsResponse(),
    var apiState: ApiState = ApiState.Initial,
) : BaseState()

