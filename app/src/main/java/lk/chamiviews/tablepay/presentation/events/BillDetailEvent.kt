package lk.chamiviews.tablepay.presentation.events

sealed class BillDetailEvent {
    data class GetProductDetails(val productIds: List<Int>) : BillDetailEvent()
    data class MarkAsPaid(val id: Int) : BillDetailEvent()
}