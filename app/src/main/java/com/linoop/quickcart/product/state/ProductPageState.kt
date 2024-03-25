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

data class ProductPageUserState(
    val showInfo: MutableState<Boolean> = mutableStateOf(false),
    val productDataState: StateHolder<ProductPageDataState> = StateHolder(ProductPageDataState()),
    val addToCartDataState: StateHolder<AddToCartDataState> = StateHolder(AddToCartDataState()),
)

data class ProductPageUserEvent(
    val getProductById: (productId: Int?) -> Unit = { _ -> },
    val addToCart: (product: Product) -> Unit = { _ -> }
)

data class ProductPageDataState(
    var product: Product = Product(),
    var apiState: ApiState = ApiState.Initial,
) : BaseState()

data class AddToCartDataState(
    var id: Long = 0,
    var apiState: ApiState = ApiState.Initial
)