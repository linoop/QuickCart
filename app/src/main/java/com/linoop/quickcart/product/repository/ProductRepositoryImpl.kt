package com.linoop.quickcart.product.repository

import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.network.ApiService
import com.linoop.quickcart.utils.Constants.HTTP_200_OK
import com.linoop.quickcart.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override fun invoke(productId: Int): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getProductById(productId)
            if (response.code() == HTTP_200_OK) response.body()?.let {
                if (response.isSuccessful) {
                    emit(Resource.Success(data = it, message = "Success"))
                } else emit(Resource.Error(message = "No record found!"))
            } else emit(Resource.Error(message = response.message()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage))
        }
    }

}