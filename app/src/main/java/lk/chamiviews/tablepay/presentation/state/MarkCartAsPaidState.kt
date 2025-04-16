package lk.chamiviews.tablepay.presentation.state

sealed class MarkCartAsPaidState {
    object Loading : MarkCartAsPaidState()
    data class Success(val message: String) : MarkCartAsPaidState()
    data class Error(val message: String) : MarkCartAsPaidState()
    object Idle : MarkCartAsPaidState()
}