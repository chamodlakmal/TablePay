package lk.chamiviews.tablepay.domain.usecase

import lk.chamiviews.tablepay.data.mapper.toProductDetail
import lk.chamiviews.tablepay.domain.model.ProductDetail
import lk.chamiviews.tablepay.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(id: Int): ProductDetail {
        return productRepository.getProductById(id).toProductDetail()
    }
}