package com.linoop.quickcart.model


import com.google.gson.annotations.SerializedName

data class GetProductsResponse(
    @SerializedName("limit")
    var limit: Int = 0,
    @SerializedName("skip")
    var skip: Int = 0,
    @SerializedName("total")
    var total: Int = 0,
    @SerializedName("products")
    var products: List<Product> = listOf()
)