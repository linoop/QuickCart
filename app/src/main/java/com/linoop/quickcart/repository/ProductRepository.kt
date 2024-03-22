package com.linoop.quickcart.repository

import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.Flow

fun interface ProductRepository {
    operator fun invoke(): Flow<Resource<GetProductsResponse>>
}