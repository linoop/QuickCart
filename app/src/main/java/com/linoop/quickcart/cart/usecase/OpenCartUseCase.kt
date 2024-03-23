package com.linoop.quickcart.cart.usecase

import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.Flow

fun interface OpenCartUseCase {
    suspend operator fun invoke(): Flow<Resource<List<Product>>>
}