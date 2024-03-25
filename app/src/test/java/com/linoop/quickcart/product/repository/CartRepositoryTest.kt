package com.linoop.quickcart.product.repository

import com.google.common.truth.Truth
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.storage.ProductDao
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CartRepositoryTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var productDao: ProductDao

    @Before
    fun setUp() {
        productDao = Mockito.mock(ProductDao::class.java)
        cartRepository = CartRepositoryImpl(productDao)
    }

    @Test
    fun `test insert a product into cart`() = runTest {
        val product = Product(brand = "Samsung")
        Mockito.`when`(productDao.insertProduct(product)).thenReturn(1)

        cartRepository.invoke(product).collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isEqualTo(1)
                Truth.assertThat(it.message).isEqualTo("Success")
            }
        }
    }
}