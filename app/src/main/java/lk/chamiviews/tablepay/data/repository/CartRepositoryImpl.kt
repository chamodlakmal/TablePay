package lk.chamiviews.tablepay.data.repository

import lk.chamiviews.tablepay.data.local.CartDatabase
import lk.chamiviews.tablepay.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDatabase: CartDatabase
) : CartRepository {
    override suspend fun markCartAsPaid(id: Int): Boolean {
        val updatedRows = cartDatabase.cartDao.markAsPaid(id)
        return updatedRows > 0
    }
}