package com.linoop.quickcart.product.usecase

import com.google.common.truth.Truth
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.product.repository.ProductRepository
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
class GetProductByIdUseCaseTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var productByIdUseCase: GetProductByIdUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Default)
        productRepository = Mockito.mock(ProductRepository::class.java)

        productByIdUseCase = GetProductByIdUseCaseImpl(productRepository)

        Mockito.`when`(productRepository.invoke(1)).thenReturn(
            flowOf(Resource.Success(Product(brand = "apple"), "Success"))
        )
        Mockito.`when`(productRepository.invoke(-1)).thenReturn(
            flowOf(Resource.Error(null, "Error"))
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test get Product By ID error`() = runBlocking {
        productByIdUseCase.invoke(-1).collect {
            Truth.assertThat(it.data).isNull()
            Truth.assertThat(it.message).isEqualTo("Error")
        }
    }

    @Test
    fun `test get Product By ID success`() = runBlocking {
        productByIdUseCase.invoke(1).collect {
            Truth.assertThat(it.data).isEqualTo(Product(brand = "apple"))
            Truth.assertThat(it.message).isEqualTo("Success")
        }
    }
}