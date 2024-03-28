package com.linoop.quickcart.utils

import com.linoop.quickcart.main.model.Product

object DummyData {
    val productList = listOf(
        Product(
            brand = "Samsung",
            title = "Galaxy",
            price = 50,
            discountPercentage = 20f,
            rating = 3f,
            stock = 10,
            thumbnail = "https://cdn.dummyjson.com/product-images/14/2.jpg",
            category = "Smart Phone",
            description = "Samsung Galaxy S23 Ultra"
        ),
        Product(
            brand = "Apple",
            title = "iPhone",
            price = 50,
            discountPercentage = 20f,
            rating = 3f,
            stock = 10,
            thumbnail = "https://cdn.dummyjson.com/product-images/14/2.jpg",
            category = "Smart Phone",
            description = "iPhone 15 Plus"
        ),
        Product(
            brand = "Mi",
            title = "Redmi",
            price = 50,
            discountPercentage = 20f,
            rating = 3f,
            stock = 10,
            thumbnail = "https://cdn.dummyjson.com/product-images/14/2.jpg",
            category = "Smart Phone",
            description = "Redmi note 10"
        ),
    )

    val product = Product(
        brand = "Samsung",
        title = "Galaxy",
        price = 50,
        discountPercentage = 20f,
        rating = 3f,
        stock = 10,
        thumbnail = "https://cdn.dummyjson.com/product-images/14/2.jpg",
        category = "Smart Phone",
        description = "Samsung Galaxy S23 Ultra"
    ).apply {
        images = listOf(
            "https://cdn.dummyjson.com/product-images/14/2.jpg",
            "https://cdn.dummyjson.com/product-images/14/2.jpg",
            "https://cdn.dummyjson.com/product-images/14/2.jpg",
            "https://cdn.dummyjson.com/product-images/14/2.jpg",
        )
    }
}