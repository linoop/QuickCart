package com.linoop.quickcart.cart.converter

import com.linoop.quickcart.cart.state.CartPageUserEvent
import com.linoop.quickcart.cart.state.CartPageUserState
import com.linoop.quickcart.cart.viewmodel.CartViewModel
import com.linoop.quickcart.utils.Converter

/**
 * CartViewModel User State Converter
 * Converts CartViewModel to CartPageUserState
 */
class CartViewModelToUserState : Converter<CartViewModel, CartPageUserState> {
    override fun convert(input: CartViewModel) = with(input) {
        CartPageUserState(
            openCartDataState = openCartDataState.value,
            deleteItemDataState = deleteItemDataState.value
        )
    }
}

/**
 * CartViewModel User Event Converter
 * Converts CartViewModel to CartPageUserState
 */
class CartViewModelToUserEvent : Converter<CartViewModel, CartPageUserEvent> {
    override fun convert(input: CartViewModel) = with(input) {
        CartPageUserEvent(
            openCart = { openCart() },
            deleteFromCart = { deleteFormCart(it) }
        )
    }
}