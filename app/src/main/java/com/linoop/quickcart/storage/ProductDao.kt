package com.linoop.quickcart.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.Constants.PRODUCT_TABLE

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long

    @Query("SELECT * FROM $PRODUCT_TABLE")
    fun getAllProducts(): List<Product>

    @Delete
    fun delete(product: Product)
}