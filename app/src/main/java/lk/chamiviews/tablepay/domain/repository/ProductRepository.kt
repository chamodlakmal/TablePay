package lk.chamiviews.tablepay.domain.repository

import lk.chamiviews.tablepay.data.model.ProductDetailDto

interface ProductRepository {
    suspend fun getProductById(id: Int): ProductDetailDto
}