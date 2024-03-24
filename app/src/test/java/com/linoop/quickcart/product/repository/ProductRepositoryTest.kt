package com.linoop.quickcart.product.repository

import com.google.common.truth.Truth
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class ProductRepositoryTest {

    @Mock
    lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        productRepository = Mockito.mock(ProductRepository::class.java)
        Mockito.`when`(productRepository.invoke(1)).thenReturn(
            flowOf(Resource.Success(Product(brand = "apple"), "Success"))
        )
        Mockito.`when`(productRepository.invoke(-1)).thenReturn(
            flowOf(Resource.Error(null, "Error"))
        )
    }

    @Test
    fun `test get product by id success`() = runTest {
        productRepository.invoke(1).collect {
            Truth.assertThat(it.data).isEqualTo(Product(brand = "apple"))
            Truth.assertThat(it.message).isEqualTo("Success")
        }
    }

    @Test
    fun `test get product by id error`() = runTest {
        productRepository.invoke(-1).collect {
            Truth.assertThat(it.data).isNull()
            Truth.assertThat(it.message).isEqualTo("Error")
        }
    }
}