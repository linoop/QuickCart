package com.linoop.quickcart.home.usecase

import androidx.paging.PagingData
import com.linoop.quickcart.model.Product
import kotlinx.coroutines.flow.Flow

fun interface GetProductsUseCase {
    suspend operator fun invoke(): Flow<PagingData<Product>>
}