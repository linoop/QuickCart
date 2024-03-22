package com.linoop.quickcart.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.linoop.quickcart.model.Product

@Database(entities = [Product::class], version = 1)
abstract class SmartHomeDatabase : RoomDatabase() {
    abstract fun getDao(): SmartHomeDao
}