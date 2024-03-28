@file:Suppress("DEPRECATION")

package com.linoop.quickcart.product.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.product.usecase.AddToCartUseCaseImpl
import com.linoop.quickcart.product.usecase.GetProductByIdUseCaseImpl
import com.linoop.quickcart.product.repository.CartRepository
import com.linoop.quickcart.product.repository.ProductRepository
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
class ProductViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var cartRepository: CartRepository
    private lateinit var productViewModel: ProductViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        productViewModel = ProductViewModel(
            getProductById = GetProductByIdUseCaseImpl(productRepository),
            addToCartUseCase = AddToCartUseCaseImpl(cartRepository)
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `test get product by ID`() = testScope.runBlockingTest {
        `when`(productRepository.invoke(1))
            .thenReturn(flowOf(Resource.Success(Product(brand = "abc"), "Success")))
        productViewModel.getProductByID(1)
        assertThat(productViewModel.productDataState.value.value.apiState).isEqualTo(ApiState.Success)
        assertThat(productViewModel.productDataState.value.value.product).isEqualTo(Product(brand = "abc"))
    }

    @Test
    fun `test add to Cart`() = testScope.runBlockingTest {
        `when`(cartRepository.invoke(Product(brand = "xyz")))
            .thenReturn(flowOf(Resource.Success(1, "Success")))
        productViewModel.addToCart(Product(brand = "xyz"))
        assertThat(productViewModel.addToCartDataState.value.value.id).isEqualTo(1)
    }
}