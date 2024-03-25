package com.linoop.quickcart.cart.usecase

import com.google.common.truth.Truth
import com.linoop.quickcart.cart.repository.CartRepository
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class DeleteFormCartUseCaseTest {

    private lateinit var deleteFormCartUseCase: DeleteFormCartUseCase
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        cartRepository = Mockito.mock(CartRepository::class.java)
        deleteFormCartUseCase = DeleteFormCartUseCaseImpl(cartRepository)
    }

    @Test
    fun `test remove from cart`() = runTest {
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