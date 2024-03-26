package com.linoop.quickcart.cart.state

import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.ViewState

/**
 * Cart Page User State
 * @param openCartDataState is view state holder [ViewState<OpenCartDataState>]
 * @param deleteItemDataState is view state holder [ViewState<DeleteItemDataState>]
 */
data class CartPageUserState(
    val openCartDataState: ViewState<OpenCartDataState> = ViewState(OpenCartDataState()),
    val deleteItemDataState: ViewState<DeleteItemDataState> = ViewState(DeleteItemDataState()),
)

/**
 * Cart Page User Events
 * @param openCart is a lambda function trigger open cart
 * @param deleteFromCart is a lambda function trigger delete a product[Product] from Database
 */
data class CartPageUserEvent(
    val openCart: () -> Unit = { },
    val deleteFromCart: (product: Product) -> Unit = { _ -> }
)

data class DeleteItemDataState(var apiState: ApiState = ApiState.Initial)
data class OpenCartDataState(
    var products: List<Product> = listOf(),
    var apiState: ApiState = ApiState.Initial,
)

