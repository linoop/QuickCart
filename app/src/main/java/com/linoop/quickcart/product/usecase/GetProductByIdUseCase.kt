package com.linoop.quickcart.product.usecase

import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.Flow

fun interface GetProductByIdUseCase {
    operator fun invoke(productId: Int): Flow<Resource<Product>>
}