package com.linoop.quickcart.product.converter

import com.linoop.quickcart.product.state.ProductPageUserEvent
import com.linoop.quickcart.product.state.ProductPageUserState
import com.linoop.quickcart.product.viewmodel.ProductViewModel
import com.linoop.quickcart.utils.Converter

/**
 * Product ViewModel Converter
 * Converts Product view model[ProductViewModel] to  product user state [ProductPageUserState]
 */
class ProductViewmodelToUserState : Converter<ProductViewModel, ProductPageUserState> {
    override fun convert(input: ProductViewModel) = with(input) {
        ProductPageUserState(
            productDataState = productDataState.value,
            addToCartDataState = addToCartDataState.value
        )
    }
}

/**
 * Product ViewModel Converter
 * Converts Product view model[ProductViewModel] to  product user event [ProductPageUserEvent]
 */
class ProductViewModelToUserEvent : Converter<ProductViewModel, ProductPageUserEvent> {
    override fun convert(input: ProductViewModel) = with(input) {
        ProductPageUserEvent(
            getProductById = { getProductByID(it) },
            addToCart = { addToCart(it) }
        )
    }
}