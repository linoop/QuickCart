package com.linoop.quickcart.network

import com.linoop.quickcart.model.GetProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ApiRoutes.GET_PRODUCTS)
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Response<GetProductsResponse>
}