package com.linoop.quickcart.usecase

import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.Flow

fun interface GetProductsUseCase {
    operator fun invoke(): Flow<Resource<GetProductsResponse>>
}