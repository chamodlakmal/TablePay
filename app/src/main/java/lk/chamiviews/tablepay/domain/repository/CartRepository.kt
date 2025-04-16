package lk.chamiviews.tablepay.domain.repository

interface CartRepository {
    suspend fun markCartAsPaid(id: Int): Boolean
}