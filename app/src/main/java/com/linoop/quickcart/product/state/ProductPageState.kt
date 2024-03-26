package com.linoop.quickcart.product.state

import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.BaseState
import com.linoop.quickcart.utils.ViewState

data class ProductPageUserState(
    val productDataState: ViewState<ProductPageDataState> = ViewState(ProductPageDataState()),
    val addToCartDataState: ViewState<AddToCartDataState> = ViewState(AddToCartDataState()),
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