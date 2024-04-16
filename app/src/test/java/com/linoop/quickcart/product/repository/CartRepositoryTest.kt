package com.linoop.quickcart.product.repository

import com.google.common.truth.Truth
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.main.storage.ProductDao
import com.linoop.quickcart.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class CartRepositoryTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var productDao: ProductDao

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        productDao = mockk<ProductDao>()
        cartRepository = CartRepositoryImpl(productDao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test insert a product into cart`() = runTest {
        val product = Product(brand = "Samsung")
        coEvery {productDao.insertProduct(product)  } returns 1
        cartRepository.invoke(product).collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isEqualTo(1)
                Truth.assertThat(it.message).isEqualTo("Success")
            }
        }
    }
}