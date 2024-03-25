package com.linoop.quickcart.home.repository

import androidx.paging.PagingData
import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.network.ApiService
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ProductListRepoTest {

    private lateinit var productListRepo: ProductListRepo
    @Mock
    private lateinit var apiService: ApiService

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        apiService = Mockito.mock(ApiService::class.java)
        productListRepo = ProductListRepoImpl(apiService)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `test product list repo`() = runTest {

    }
}