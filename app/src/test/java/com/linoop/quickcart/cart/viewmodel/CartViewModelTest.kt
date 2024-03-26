package com.linoop.quickcart.cart.viewmodel

import com.google.common.truth.Truth
import com.linoop.quickcart.cart.repository.CartRepositoryImpl
import com.linoop.quickcart.cart.usecase.DeleteFormCartUseCaseImpl
import com.linoop.quickcart.cart.usecase.OpenCartUseCaseImpl
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.main.storage.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
class CartViewModelTest {

    private lateinit var dao: ProductDao
    private lateinit var cartViewModel: CartViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Default)
        dao = Mockito.mock(ProductDao::class.java)
        val cartRepository = CartRepositoryImpl(dao)
        val openCartUseCase = OpenCartUseCaseImpl(cartRepository)
        val deleteFormCartUseCase = DeleteFormCartUseCaseImpl(cartRepository)
        cartViewModel = CartViewModel(openCartUseCase, deleteFormCartUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test open cart`() = runBlocking {
        val testData = listOf(
            Product(brand = "apple"),
            Product(brand = "samsung"),
        )
        Mockito.`when`(dao.getAllProducts()).thenReturn(testData)
        cartViewModel.openCart()
        delay(10)
        Truth.assertThat(cartViewModel.openCartDataState.value.value.products).isEqualTo(testData)
    }
}