package com.linoop.quickcart.home.usecase

import androidx.paging.PagingData
import com.linoop.quickcart.home.repository.ProductListRepo
import com.linoop.quickcart.main.model.Product
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetProductsUseCaseTest {

    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var productListRepo: ProductListRepo

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        productListRepo = mockk<ProductListRepo>()
        getProductsUseCase = GetProductsUseCaseImpl(productListRepo)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `test get products`() = runTest {
        val testData = PagingData.from(
            listOf(
                Product(brand = "apple"),
                Product(brand = "samsung"),
            )
        )
        coEvery { productListRepo.invoke() } returns flowOf(testData)
        val emittedData = productListRepo.invoke().first()
        TestCase.assertEquals(testData, emittedData)
    }
}