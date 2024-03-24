package com.linoop.quickcart.product.repository

import com.google.common.truth.Truth
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CartRepositoryTest {

    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        cartRepository = Mockito.mock(CartRepository::class.java)
    }

    @Test
    fun `test insert a product into cart`() = runTest {
        val product = Product(brand = "Samsung")
        Mockito.`when`(cartRepository.invoke(product))
            .thenReturn(flow { emit(Resource.Success(1, "Success")) })
        cartRepository.invoke(product).collect {
            Truth.assertThat(it.data).isEqualTo(1)
            Truth.assertThat(it.message).isEqualTo("Success")
        }
    }
}