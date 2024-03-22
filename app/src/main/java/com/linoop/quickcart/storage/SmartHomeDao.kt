package com.linoop.quickcart.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.linoop.quickcart.model.Product

@Dao
interface SmartHomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long
}