package com.linoop.quickcart.cart.usecase

import com.google.common.truth.Truth
import com.linoop.quickcart.cart.repository.CartRepository
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class OpenCartUseCaseTest {

    private lateinit var openCartUseCase: OpenCartUseCase
    private lateinit var cartRepository: CartRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        cartRepository = mockk<CartRepository>()
        openCartUseCase = OpenCartUseCaseImpl(cartRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test open cart`() = runTest {
        val products = listOf(
            Product(brand = "Samsung", title = "Galaxy"),
            Product(brand = "Apple", title = "iPhone"),
            Product(brand = "Mi", title = "Redmi"),
        )
        coEvery { cartRepository.getAllItems() } returns flowOf(Resource.Success(products, "Success"))
        openCartUseCase.invoke().collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isEqualTo(products)
                Truth.assertThat(it.message).isEqualTo("Success")
            }
        }
    }
}