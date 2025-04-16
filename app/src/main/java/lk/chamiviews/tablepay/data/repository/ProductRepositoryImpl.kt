package lk.chamiviews.tablepay.data.repository

import lk.chamiviews.tablepay.data.model.ProductDetailDto
import lk.chamiviews.tablepay.data.remote.ApiService
import lk.chamiviews.tablepay.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {
    override suspend fun getProductById(id: Int): ProductDetailDto {
        return apiService.getProduct(id)
    }
}