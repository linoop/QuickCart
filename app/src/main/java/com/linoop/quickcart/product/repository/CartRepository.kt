package com.linoop.quickcart.product.repository

import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.Flow

fun interface CartRepository {
    operator fun invoke(product: Product): Flow<Resource<Long>>
}