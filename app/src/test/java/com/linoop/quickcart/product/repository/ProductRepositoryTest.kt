package com.linoop.quickcart.product.repository

import com.google.common.truth.Truth
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.network.ApiService
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

class ProductRepositoryTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = Mockito.mock(ApiService::class.java)
        productRepository = ProductRepositoryImpl(apiService)
    }

    @Test
    fun `test get product by id success`() = runTest {
        Mockito.`when`(apiService.getProductById(1)).thenReturn(
            Response.success(Product(brand = "apple"))
        )
        productRepository.invoke(1).collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isEqualTo(Product(brand = "apple"))
                Truth.assertThat(it.message).isEqualTo("Success")
            }
        }
    }

    @Test
    fun `test get product by id error`() = runTest {
        val responseBody: ResponseBody = "Response.error()".toResponseBody("application/json".toMediaTypeOrNull())
        Mockito.`when`(apiService.getProductById(-1)).thenReturn(
            Response.error(500, responseBody)
        )
        productRepository.invoke(-1).collect {
            if (it is Resource.Error) {
                Truth.assertThat(it.data).isNull()
                Truth.assertThat(it.message).isEqualTo("Response.error()")
            }
        }
    }
}