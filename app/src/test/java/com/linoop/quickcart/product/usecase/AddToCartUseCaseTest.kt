package com.linoop.quickcart.product.usecase

import com.google.common.truth.Truth
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.product.repository.CartRepository
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class AddToCartUseCaseTest {

    private lateinit var aadToCartUseCase: AddToCartUseCase
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        cartRepository = Mockito.mock(CartRepository::class.java)
        aadToCartUseCase = AddToCartUseCaseImpl(cartRepository)
    }

    @Test
    fun `test aad to cart error`() = runTest {
        Mockito.`when`(cartRepository.invoke(Product())).thenReturn(
            flowOf(Resource.Error(null, "Error"))
        )
        aadToCartUseCase.invoke(Product()).collect {
            Truth.assertThat(it.data).isNull()
            Truth.assertThat(it.message).isEqualTo("Error")
        }
    }

    @Test
    fun `test aad to cart success`() = runTest {
        val product = Product(brand = "apple")
        Mockito.`when`(cartRepository.invoke(product)).thenReturn(
            flowOf(Resource.Success(1, "Success"))
        )
        aadToCartUseCase.invoke(product).collect {
            Truth.assertThat(it.data).isEqualTo(1)
            Truth.assertThat(it.message).isEqualTo("Success")
        }
    }
}