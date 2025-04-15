package lk.chamiviews.tablepay.data.model

data class CartResponse(
    val carts: List<CartDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
