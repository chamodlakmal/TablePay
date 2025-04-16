package lk.chamiviews.tablepay.domain.usecase

import lk.chamiviews.tablepay.domain.repository.CartRepository
import javax.inject.Inject

class MarkCartAsPaidUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(id: Int): Boolean {
        return cartRepository.markCartAsPaid(id)
    }
}