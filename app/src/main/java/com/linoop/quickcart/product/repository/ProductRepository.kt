package com.linoop.quickcart.product.repository

import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.Flow

fun interface ProductRepository {
    operator fun invoke(productId: Int): Flow<Resource<Product>>
}