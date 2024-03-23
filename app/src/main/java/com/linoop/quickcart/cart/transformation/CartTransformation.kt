package com.linoop.quickcart.cart.transformation

import com.linoop.quickcart.cart.state.CartPageUserEvent
import com.linoop.quickcart.cart.state.CartPageUserState
import com.linoop.quickcart.cart.viewmodel.CartViewModel
import com.linoop.quickcart.utils.Transformer

class CartViewModelToUserState : Transformer<CartViewModel, CartPageUserState> {
    override fun transform(input: CartViewModel) = with(input) {
        CartPageUserState(
            openCartDataState = openCartDataState.value,
            deleteItemDataState = deleteItemDataState.value
        )
    }
}

class CartViewModelToUserEvent : Transformer<CartViewModel, CartPageUserEvent> {
    override fun transform(input: CartViewModel) = with(input) {
        CartPageUserEvent(
            openCart = { openCart() },
            deleteFromCart = { deleteFormCart(it) }
        )
    }
}