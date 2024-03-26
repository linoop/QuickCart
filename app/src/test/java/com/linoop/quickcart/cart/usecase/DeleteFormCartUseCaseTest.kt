package com.linoop.quickcart.cart.usecase

import com.google.common.truth.Truth
import com.linoop.quickcart.cart.repository.CartRepository
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
class DeleteFormCartUseCaseTest {

    private lateinit var deleteFormCartUseCase: DeleteFormCartUseCase
    private lateinit var cartRepository: CartRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        MockitoAnnotations.initMocks(this)
        cartRepository = Mockito.mock(CartRepository::class.java)
        deleteFormCartUseCase = DeleteFormCartUseCaseImpl(cartRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test remove from cart`() = runBlocking {
        val testData = Product(brand = "Samsung")
        Mockito.`when`(cartRepository.deleteItem(testData))
            .thenReturn(flowOf(Resource.Success(true, "Success")))
        deleteFormCartUseCase.invoke(testData).collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isTrue()
            }
        }
    }
}