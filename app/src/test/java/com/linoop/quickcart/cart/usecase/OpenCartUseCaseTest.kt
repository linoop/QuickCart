package com.linoop.quickcart.cart.usecase

import com.google.common.truth.Truth
import com.linoop.quickcart.cart.repository.CartRepository
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class OpenCartUseCaseTest {

    private lateinit var openCartUseCase: OpenCartUseCase
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        cartRepository = Mockito.mock(CartRepository::class.java)
        openCartUseCase = OpenCartUseCaseImpl(cartRepository)
    }

    @Test
    fun `test open cart`() = runTest {
        val products = listOf(
            Product(brand = "Samsung", title = "Galaxy"),
            Product(brand = "Apple", title = "iPhone"),
            Product(brand = "Mi", title = "Redmi"),
        )
        Mockito.`when`(cartRepository.getAllItems())
            .thenReturn(flowOf(Resource.Success(products, "Success")))
        openCartUseCase.invoke().collect {
            if (it is Resource.Success) {
                Truth.assertThat(it.data).isEqualTo(products)
            }
        }
    }
}