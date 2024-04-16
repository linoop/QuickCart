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

class DeleteFormCartUseCaseTest {

    private lateinit var deleteFormCartUseCase: DeleteFormCartUseCase
    private lateinit var cartRepository: CartRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        cartRepository = mockk<CartRepository>()
        deleteFormCartUseCase = DeleteFormCartUseCaseImpl(cartRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test remove from cart`() = runTest {
        val testData = Product(brand = "Samsung")
        coEvery {  cartRepository.deleteItem(testData) } returns flowOf(Resource.Success(true, "Success"))
        deleteFormCartUseCase.invoke(testData).collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isTrue()
                Truth.assertThat(it.message).isEqualTo("Success")
            }
        }
    }
}