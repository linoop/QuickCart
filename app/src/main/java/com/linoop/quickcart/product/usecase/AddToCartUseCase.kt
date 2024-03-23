package com.linoop.quickcart.product.usecase

import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.Flow

fun interface AddToCartUseCase {
    operator fun invoke(product: Product): Flow<Resource<Long>>
}