package lk.chamiviews.tablepay.domain.model

data class Cart(
    val id: Int,
    val total: Double,
    val products: List<Product>,
    val isPaid: Boolean
)
