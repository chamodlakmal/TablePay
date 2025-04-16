package lk.chamiviews.tablepay.data.model

import androidx.room.Entity

@Entity(tableName = "local_product")
data class LocalProduct(
    val id: Int,
    val price: Double,
    val quantity: Int
)