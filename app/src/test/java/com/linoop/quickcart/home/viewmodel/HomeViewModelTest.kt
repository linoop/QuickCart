package com.linoop.quickcart.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.filter
import com.google.common.truth.Truth
import com.linoop.quickcart.home.repository.ProductListRepo
import com.linoop.quickcart.home.usecase.GetProductsUseCase
import com.linoop.quickcart.home.usecase.GetProductsUseCaseImpl
import com.linoop.quickcart.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var productListRepo: ProductListRepo

    private lateinit var homeViewModel: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        MockitoAnnotations.initMocks(this)
        productListRepo = Mockito.mock(ProductListRepo::class.java)
        homeViewModel = HomeViewModel(GetProductsUseCaseImpl(productListRepo))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test list product`() = runTest {

    }
}