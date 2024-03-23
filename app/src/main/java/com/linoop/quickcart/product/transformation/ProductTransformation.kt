package com.linoop.quickcart.product.transformation

import com.linoop.quickcart.product.state.ProductPageUserEvent
import com.linoop.quickcart.product.state.ProductPageUserState
import com.linoop.quickcart.product.viewmodel.ProductViewModel
import com.linoop.quickcart.utils.Transformer

class ProductViewmodelToUserState : Transformer<ProductViewModel, ProductPageUserState> {
    override fun transform(input: ProductViewModel) = with(input) {
        ProductPageUserState(
            productDataState = productDataState.value,
            addToCartDataState = addToCartDataState.value
        )
    }
}

class ProductViewModelToUserEvent : Transformer<ProductViewModel, ProductPageUserEvent> {
    override fun transform(input: ProductViewModel) = with(input) {
        ProductPageUserEvent(
            getProductById = { getProductByID(it) },
            addToCart = { addToCart(it) }
        )
    }
}