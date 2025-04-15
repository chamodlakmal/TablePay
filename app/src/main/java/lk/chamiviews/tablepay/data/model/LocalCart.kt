package lk.chamiviews.tablepay.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_cart")
data class LocalCart(
    @PrimaryKey
    val id: Int,
    val total: Double,
    val products: List<LocalProduct>,
    val isPaid: Boolean
)