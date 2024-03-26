package com.linoop.quickcart.main.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.linoop.quickcart.main.model.Product

@Database(entities = [Product::class], version = 1)
abstract class QuickCartDatabase : RoomDatabase() {
    abstract fun getDao(): ProductDao
}