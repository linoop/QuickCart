package com.linoop.quickcart.product.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.product.repository.CartRepository
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
class AddToCartUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var aadToCartUseCase: AddToCartUseCase

    lateinit var cartRepository: CartRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        MockitoAnnotations.initMocks(this)
        cartRepository = Mockito.mock(CartRepository::class.java)
        aadToCartUseCase = AddToCartUseCaseImpl(cartRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
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