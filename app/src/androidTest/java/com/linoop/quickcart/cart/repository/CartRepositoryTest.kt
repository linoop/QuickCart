package com.linoop.quickcart.cart.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.main.storage.ProductDao
import com.linoop.quickcart.main.storage.QuickCartDatabase
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartRepositoryTest {
    private lateinit var db: QuickCartDatabase
    private lateinit var dao: ProductDao
    private lateinit var cartRepository: CartRepository
    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            QuickCartDatabase::class.java
        ).build()
        dao = db.getDao()
        cartRepository = CartRepositoryImpl(dao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testOpenCart() = runBlocking {
        val products = listOf(
            Product(brand = "Samsung", title = "Galaxy"),
            Product(brand = "Apple", title = "iPhone"),
            Product(brand = "Mi", title = "Redmi"),
        )
        dao.insertAll(*products.toTypedArray())
        cartRepository.getAllItems().collect {
            if(it is Resource.Success)
                Truth.assertThat(it.data).contains(Product(brand = "Samsung", title = "Galaxy"))
        }
    }

    @Test
    fun testRemoveFromCart() = runBlocking {
        val products = listOf(
            Product(brand = "Samsung", title = "Galaxy"),
            Product(brand = "Apple", title = "iPhone"),
            Product(brand = "Mi", title = "Redmi"),
        )
        dao.insertAll(*products.toTypedArray())
        val allProducts = dao.getAllProducts()
        val product1 = allProducts[0]
        val product2 = allProducts[1]
        cartRepository.deleteItem(product2).collect {
            if (it is Resource.Success) {
                val allProductsAfter = dao.getAllProducts()
                Truth.assertThat(allProductsAfter).contains(product1)
                Truth.assertThat(allProductsAfter).doesNotContain(product2)
            }
        }
    }
}