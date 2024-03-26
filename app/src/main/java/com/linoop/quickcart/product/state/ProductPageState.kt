package com.linoop.quickcart.product.state

import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.BaseState
import com.linoop.quickcart.utils.ViewState

/**
 * Product Details Page User State
 * @param productDataState is a view state holder to show product details
 * @param addToCartDataState is a view state holder to add a product in to the cart
 */
data class ProductPageUserState(
    val productDataState: ViewState<ProductPageDataState> = ViewState(ProductPageDataState()),
    val addToCartDataState: ViewState<AddToCartDataState> = ViewState(AddToCartDataState()),
)

/**
 * Product Details Page User Event
 * @param getProductById is a lambda to trigger get product by ID
 * @param addToCart is a lambda to a product[Product] to the database
 */
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