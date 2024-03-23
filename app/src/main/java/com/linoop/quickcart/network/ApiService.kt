package com.linoop.quickcart.network

import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(ApiRoutes.GET_PRODUCTS)
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Response<GetProductsResponse>

    @GET(ApiRoutes.GET_PRODUCT_BY_ID)
    suspend fun getProductById(
        @Path("product_id") productId: Int
    ): Response<Product>
}