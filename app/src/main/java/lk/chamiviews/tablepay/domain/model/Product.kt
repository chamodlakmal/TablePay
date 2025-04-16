package lk.chamiviews.tablepay.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val price: Double,
    val quantity: Int,
    val thumbnail: String
)