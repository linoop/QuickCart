package com.linoop.quickcart.product.usecase

import com.google.common.truth.Truth
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.product.repository.ProductRepository
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetProductByIdUseCaseTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var productByIdUseCase: GetProductByIdUseCase

    @Before
    fun setUp() {
        productRepository = Mockito.mock(ProductRepository::class.java)

        productByIdUseCase = GetProductByIdUseCaseImpl(productRepository)

        Mockito.`when`(productRepository.invoke(1)).thenReturn(
            flowOf(Resource.Success(Product(brand = "apple"), "Success"))
        )
        Mockito.`when`(productRepository.invoke(-1)).thenReturn(
            flowOf(Resource.Error(null, "Error"))
        )
    }

    @Test
    fun `test get Product By ID error`() = runTest {
        productByIdUseCase.invoke(-1).collect {
            Truth.assertThat(it.data).isNull()
            Truth.assertThat(it.message).isEqualTo("Error")
        }
    }

    @Test
    fun `test get Product By ID success`() = runTest {
        productByIdUseCase.invoke(1).collect {
            Truth.assertThat(it.data).isEqualTo(Product(brand = "apple"))
            Truth.assertThat(it.message).isEqualTo("Success")
        }
    }
}