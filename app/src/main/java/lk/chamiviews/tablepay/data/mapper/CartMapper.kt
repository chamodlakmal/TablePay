package lk.chamiviews.tablepay.data.mapper

import lk.chamiviews.tablepay.data.model.CartDto
import lk.chamiviews.tablepay.data.model.LocalCart
import lk.chamiviews.tablepay.data.model.LocalProduct
import lk.chamiviews.tablepay.data.model.ProductDto
import lk.chamiviews.tablepay.domain.model.Cart
import lk.chamiviews.tablepay.domain.model.Product

// DTO to Entity

fun CartDto.toLocalCart(isPaid: Boolean = false): LocalCart {
    return LocalCart(
        id = id,
        total = total,
        products = products.map { it.toLocalProduct() },
        isPaid = isPaid
    )
}

fun ProductDto.toLocalProduct(): LocalProduct {
    return LocalProduct(
        id = id,
        price = price,
        quantity = quantity
    )
}

fun LocalProduct.toDomain(): Product {
    return Product(
        id = id,
        price = price,
        quantity = quantity
    )
}

fun LocalCart.toDomain(): Cart {
    return Cart(
        id = id,
        total = total,
        products = products.map { it.toDomain() },
        isPaid = isPaid
    )
}




