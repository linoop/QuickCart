package com.linoop.quickcart.product.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.network.ApiService
import com.linoop.quickcart.product.repository.CartRepositoryImpl
import com.linoop.quickcart.product.repository.ProductRepositoryImpl
import com.linoop.quickcart.product.usecase.AddToCartUseCaseImpl
import com.linoop.quickcart.product.usecase.GetProductByIdUseCaseImpl
import com.linoop.quickcart.storage.ProductDao
import com.linoop.quickcart.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@Suppress("DEPRECATION")
class ProductViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var productDao: ProductDao

    private lateinit var productViewModel: ProductViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Default)
        productViewModel = ProductViewModel(
            getProductById = GetProductByIdUseCaseImpl(ProductRepositoryImpl(apiService)),
            addToCartUseCase = AddToCartUseCaseImpl(CartRepositoryImpl(productDao))
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test get product by ID`() = runBlocking {
        `when`(apiService.getProductById(1)).thenReturn(Response.success(Product(brand = "abc")))
        productViewModel.getProductByID(1)
        delay(10)
        assertThat(productViewModel.productDataState.value.value.apiState).isEqualTo(ApiState.Success)
        assertThat(productViewModel.productDataState.value.value.product).isEqualTo(Product(brand = "abc"))
    }

    @Test
    fun `test add to Cart`() = runBlocking {
        `when`(productDao.insertProduct(Product(brand = "xyz"))).thenReturn(1)
        productViewModel.addToCart(Product(brand = "xyz"))
        delay(510)
        assertThat(productViewModel.addToCartDataState.value.value.id).isEqualTo(1)
    }
}