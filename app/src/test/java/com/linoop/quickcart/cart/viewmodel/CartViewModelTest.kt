@file:Suppress("DEPRECATION")

package com.linoop.quickcart.cart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.linoop.quickcart.MainCoroutineRule
import com.linoop.quickcart.cart.repository.CartRepository
import com.linoop.quickcart.cart.usecase.DeleteFormCartUseCaseImpl
import com.linoop.quickcart.cart.usecase.OpenCartUseCaseImpl
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
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
class CartViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var cartRepository: CartRepository
    private lateinit var cartViewModel: CartViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        cartRepository = Mockito.mock(CartRepository::class.java)
        val openCartUseCase = OpenCartUseCaseImpl(cartRepository)
        val deleteFormCartUseCase = DeleteFormCartUseCaseImpl(cartRepository)
        cartViewModel = CartViewModel(openCartUseCase, deleteFormCartUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `test open cart`() = runBlocking {
        val testData = listOf(
            Product(brand = "apple"),
            Product(brand = "samsung"),
        )
        Mockito.`when`(cartRepository.getAllItems())
            .thenReturn(flowOf(Resource.Success(testData, "Success")))
        cartViewModel.openCart()
        delay(10)
        assertThat(cartViewModel.openCartDataState.value.value.products).isEqualTo(testData)
    }

    @Test
    fun `test openCart`() = testScope.runBlockingTest {
        val testData = listOf(
            Product(brand = "apple"),
            Product(brand = "samsung")
        )
        val mockSuccessResponse = Resource.Success(testData, "Success")
        Mockito.`when`(cartRepository.getAllItems()).thenReturn(flowOf(mockSuccessResponse))
        cartViewModel.openCart()
        testScheduler.apply { advanceTimeBy(0);runCurrent() }
        assertThat(cartViewModel.openCartDataState.value.value.apiState).isEqualTo(ApiState.Success)
        assertThat(cartViewModel.openCartDataState.value.value.products).isEqualTo(testData)
    }

}