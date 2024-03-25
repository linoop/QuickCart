package com.linoop.quickcart.product.repository

import com.google.common.truth.Truth
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.storage.ProductDao
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
class CartRepositoryTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var productDao: ProductDao

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        MockitoAnnotations.initMocks(this)
        productDao = Mockito.mock(ProductDao::class.java)
        cartRepository = CartRepositoryImpl(productDao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test insert a product into cart`() = runBlocking {
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