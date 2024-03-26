package com.linoop.quickcart.cart.repository

import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getAllItems(): Flow<Resource<List<Product>>>
    suspend fun deleteItem(product: Product): Flow<Resource<Boolean>>
}