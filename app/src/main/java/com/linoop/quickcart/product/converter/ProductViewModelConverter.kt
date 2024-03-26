package com.linoop.quickcart.product.converter

import com.linoop.quickcart.product.state.ProductPageUserEvent
import com.linoop.quickcart.product.state.ProductPageUserState
import com.linoop.quickcart.product.viewmodel.ProductViewModel
import com.linoop.quickcart.utils.Converter

class ProductViewmodelToUserState : Converter<ProductViewModel, ProductPageUserState> {
    override fun convert(input: ProductViewModel) = with(input) {
        ProductPageUserState(
            productDataState = productDataState.value,
            addToCartDataState = addToCartDataState.value
        )
    }
}

class ProductViewModelToUserEvent : Converter<ProductViewModel, ProductPageUserEvent> {
    override fun convert(input: ProductViewModel) = with(input) {
        ProductPageUserEvent(
            getProductById = { getProductByID(it) },
            addToCart = { addToCart(it) }
        )
    }
}