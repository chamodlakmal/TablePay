package lk.chamiviews.tablepay.data.model

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

// Entity to DTO

fun LocalCart.toCartDto(): CartDto {
    return CartDto(
        id = id,
        total = total,
        products = products.map { it.toProductDto() }
    )
}

fun LocalProduct.toProductDto(): ProductDto {
    return ProductDto(
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

fun Product.toLocal(): LocalProduct {
    return LocalProduct(
        id = id,
        price = price,
        quantity = quantity
    )
}

fun Cart.toLocal(): LocalCart {
    return LocalCart(
        id = id,
        total = total,
        products = products.map { it.toLocal() },
        isPaid = isPaid
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




