package com.linoop.quickcart.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.linoop.quickcart.home.repository.ProductListRepo
import com.linoop.quickcart.home.usecase.GetProductsUseCaseImpl
import com.linoop.quickcart.main.model.Product
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var productListRepo: ProductListRepo

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        productListRepo = Mockito.mock(ProductListRepo::class.java)
        homeViewModel = HomeViewModel(GetProductsUseCaseImpl(productListRepo))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `test list product`() = testScope.runBlockingTest {
        val testData = PagingData.from(
            listOf(
                Product(brand = "apple"),
                Product(brand = "samsung"),
            )
        )
        Mockito.`when`(productListRepo.invoke()).thenReturn(flowOf(testData))
        homeViewModel.productState.value = testData
        val emittedData = homeViewModel.productState.first()
        assertEquals(testData, emittedData)
    }
}