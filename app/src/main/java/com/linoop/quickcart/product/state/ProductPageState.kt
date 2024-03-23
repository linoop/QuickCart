package com.linoop.quickcart.product.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.BaseState
import com.linoop.quickcart.utils.Constants
import com.linoop.quickcart.utils.StateHolder

@Stable
data class ProductPageInitialValue(
    var query: String = Constants.EMPTY,
)
data class ProductPageUserState(
    val initialValue: ProductPageInitialValue = ProductPageInitialValue(),
    val queryError: State<Int?> = mutableStateOf(null),
    val showInfo: MutableState<Boolean> = mutableStateOf(false),
    val dataState: StateHolder<ProductPageDataState> = StateHolder(ProductPageDataState())
)

data class ProductPageUserEvent(
    val getProductById: (productId: Int?) -> Unit = { _ -> }
)

data class ProductPageDataState(
    var product: Product = Product(),
    var apiState: ApiState = ApiState.Initial,
) : BaseState()