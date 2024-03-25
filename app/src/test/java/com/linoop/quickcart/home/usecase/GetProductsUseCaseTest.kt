package com.linoop.quickcart.home.usecase

import androidx.paging.PagingData
import com.linoop.quickcart.home.repository.ProductListRepo
import com.linoop.quickcart.model.Product
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetProductsUseCaseTest {

    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var productListRepo: ProductListRepo

    @Before
    fun setUp() {
        productListRepo = Mockito.mock(ProductListRepo::class.java)
        getProductsUseCase = GetProductsUseCaseImpl(productListRepo)
    }

    @Test
    fun `test get products`() = runTest {
        val testData = PagingData.from(
            listOf(
                Product(brand = "apple"),
                Product(brand = "samsung"),
            )
        )
        Mockito.`when`(productListRepo.invoke()).thenReturn(flowOf(testData))
        val emittedData = productListRepo.invoke().first()
        TestCase.assertEquals(testData, emittedData)
    }
}