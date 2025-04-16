package lk.chamiviews.tablepay.presentation.state

import lk.chamiviews.tablepay.domain.model.ProductDetail

sealed class ProductDetailState {
    object Loading : ProductDetailState()
    data class Success(val productDetail: ProductDetail) : ProductDetailState()
    data class Error(val message: String) : ProductDetailState()
}