package com.linoop.quickcart.repository

import androidx.paging.PagingData
import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.model.Product
import kotlinx.coroutines.flow.Flow

fun interface PagProductRepo {
    operator fun invoke(): Flow<PagingData<Product>>
}