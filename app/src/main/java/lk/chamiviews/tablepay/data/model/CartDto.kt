package lk.chamiviews.tablepay.data.model

data class CartDto(
    val id: Int,
    val total: Double,
    val products: List<ProductDto>
)