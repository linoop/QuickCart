package com.linoop.quickcart.usecase

import androidx.paging.PagingData
import com.linoop.quickcart.model.Product
import kotlinx.coroutines.flow.Flow

fun interface PagProductUseCase {
    suspend operator fun invoke(): Flow<PagingData<Product>>
}