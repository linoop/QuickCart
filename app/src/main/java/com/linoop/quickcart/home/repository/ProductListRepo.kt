package com.linoop.quickcart.home.repository

import androidx.paging.PagingData
import com.linoop.quickcart.model.Product
import kotlinx.coroutines.flow.Flow

fun interface ProductListRepo {
    operator fun invoke(): Flow<PagingData<Product>>
}