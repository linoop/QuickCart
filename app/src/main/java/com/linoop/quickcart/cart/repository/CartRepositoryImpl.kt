package com.linoop.quickcart.cart.repository

import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.main.storage.ProductDao
import com.linoop.quickcart.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class CartRepositoryImpl @Inject constructor(
    private val dao: ProductDao
) : CartRepository {
    override suspend fun getAllItems(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        val items = dao.getAllProducts()
        if (items.isNotEmpty()) emit(Resource.Success(items, message = "Success"))
        else emit(Resource.Error(message = "No record found"))
    }

    override suspend fun deleteItem(product: Product): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading())
        dao.delete(product)
        emit(Resource.Success(true, message = "Success"))
    }
}