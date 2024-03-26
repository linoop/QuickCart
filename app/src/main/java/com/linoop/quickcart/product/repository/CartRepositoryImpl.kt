package com.linoop.quickcart.product.repository

import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.main.storage.ProductDao
import com.linoop.quickcart.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class CartRepositoryImpl @Inject constructor(private val dao: ProductDao) : CartRepository {
    override fun invoke(product: Product): Flow<Resource<Long>> = flow {
        emit(Resource.Loading())
        delay(500)
        val id = dao.insertProduct(product)
        if (id > 0) emit(Resource.Success(data = id, message = "Success"))
        else emit(Resource.Error(message = "Database error"))
    }
}