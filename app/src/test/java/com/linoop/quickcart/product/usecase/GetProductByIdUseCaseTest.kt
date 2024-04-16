package com.linoop.quickcart.product.usecase

import com.google.common.truth.Truth
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.product.repository.ProductRepository
import com.linoop.quickcart.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetProductByIdUseCaseTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var productByIdUseCase: GetProductByIdUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        productRepository = mockk<ProductRepository>()
        productByIdUseCase = GetProductByIdUseCaseImpl(productRepository)
        coEvery { productRepository.invoke(1) } returns  flowOf(Resource.Success(Product(brand = "apple"), "Success"))
        coEvery {  productRepository.invoke(-1) } returns  flowOf(Resource.Error(null, "Error"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
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