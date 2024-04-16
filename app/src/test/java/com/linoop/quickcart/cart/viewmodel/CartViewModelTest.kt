@file:Suppress("DEPRECATION")

package com.linoop.quickcart.cart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.linoop.quickcart.MainCoroutineRule
import com.linoop.quickcart.cart.repository.CartRepository
import com.linoop.quickcart.cart.usecase.DeleteFormCartUseCaseImpl
import com.linoop.quickcart.cart.usecase.OpenCartUseCaseImpl
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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
        cartRepository = mockk<CartRepository>()
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
    fun `test open cart`() = testScope.runBlockingTest {
        val testData = listOf(
            Product(brand = "apple"),
            Product(brand = "samsung"),
        )
        coEvery { cartRepository.getAllItems() } returns flowOf(Resource.Success(testData, "Success"))
        cartViewModel.openCart()
        //testScope.testScheduler.apply { advanceTimeBy(1000); runCurrent() }
        delay(1000)
        assertThat(cartViewModel.openCartDataState.value.value.products).isEqualTo(testData)
    }
}