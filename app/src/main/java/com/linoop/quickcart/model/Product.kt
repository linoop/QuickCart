package com.linoop.quickcart.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.linoop.quickcart.utils.Constants.EMPTY
import com.linoop.quickcart.utils.Constants.PRODUCT_TABLE

@Entity(tableName = PRODUCT_TABLE)
data class Product(
    @SerializedName("brand")
    var brand: String = EMPTY,
    @SerializedName("category")
    var category: String = EMPTY,
    @SerializedName("description")
    var description: String = EMPTY,
    @SerializedName("discountPercentage")
    var discountPercentage: Double? = null,
    @SerializedName("price")
    var price: Int = 0,
    @SerializedName("rating")
    var rating: Double? = null,
    @SerializedName("stock")
    var stock: Int? = null,
    @SerializedName("thumbnail")
    var thumbnail: String = EMPTY,
    @SerializedName("title")
    var title: String = EMPTY
) {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    var productId: Int? = null

    @Ignore
    @SerializedName("images")
    var images: List<String>? = null
}