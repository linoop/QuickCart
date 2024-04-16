package com.linoop.quickcart.product.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.main.network.ApiService
import com.linoop.quickcart.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


class ProductRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var productRepository: ProductRepository


    private lateinit var apiService: ApiService

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        apiService = mockk<ApiService>()
        productRepository = ProductRepositoryImpl(apiService)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test get product by id success`() = runTest {
        coEvery { apiService.getProductById(1) } returns  Response.success(Product(brand = "apple"))
        productRepository.invoke(1).collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isEqualTo(Product(brand = "apple"))
                Truth.assertThat(it.message).isEqualTo("Success")
            }
        }
    }

    @Test
    fun `test get product by id error`() = runTest {
        val responseBody: ResponseBody =
            "Response.error()".toResponseBody("application/json".toMediaTypeOrNull())
        coEvery { apiService.getProductById(-1) } returns  Response.error(500, responseBody)
        productRepository.invoke(-1).collect {
            if (it is Resource.Error) {
                Truth.assertThat(it.data).isNull()
                Truth.assertThat(it.message).isEqualTo("Response.error()")
            }
        }
    }
}