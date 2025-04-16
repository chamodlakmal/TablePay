package lk.chamiviews.tablepay.data.mapper

import lk.chamiviews.tablepay.data.model.ProductDetailDto
import lk.chamiviews.tablepay.domain.model.ProductDetail

fun ProductDetailDto.toProductDetail(): ProductDetail {
    return ProductDetail(
        id = this.id,
        title = this.title
    )
}

