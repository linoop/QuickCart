package com.linoop.quickcart.cart.state

import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.StateHolder

data class CartPageUserState(
    val openCartDataState: StateHolder<OpenCartDataState> = StateHolder(OpenCartDataState()),
    val deleteItemDataState: StateHolder<DeleteItemDataState> = StateHolder(DeleteItemDataState()),
)

data class CartPageUserEvent(
    val openCart: () -> Unit = { },
    val deleteFromCart: (product: Product) -> Unit = { _ -> }
)

data class DeleteItemDataState(var apiState: ApiState = ApiState.Initial)
data class OpenCartDataState(
    var products: List<Product> = listOf(),
    var apiState: ApiState = ApiState.Initial,
)

