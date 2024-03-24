package com.linoop.quickcart

import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.network.ApiService
import retrofit2.Response

open class ApiServiceImplTest : ApiService {
    override suspend fun getProducts(limit: Int, skip: Int): Response<GetProductsResponse> =
        Response.success(GetProductsResponse())


    override suspend fun getProductById(productId: Int): Response<Product> =
        Response.success(Product(brand = "Samsung"))
}