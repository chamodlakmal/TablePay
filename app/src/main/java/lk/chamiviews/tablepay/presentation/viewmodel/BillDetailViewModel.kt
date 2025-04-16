package lk.chamiviews.tablepay.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lk.chamiviews.tablepay.domain.usecase.GetProductDetailUseCase
import lk.chamiviews.tablepay.domain.usecase.MarkCartAsPaidUseCase
import lk.chamiviews.tablepay.presentation.events.BillDetailEvent
import lk.chamiviews.tablepay.presentation.state.MarkCartAsPaidState
import lk.chamiviews.tablepay.presentation.state.ProductDetailState
import javax.inject.Inject

@HiltViewModel
class BillDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val markCartAsPaidUseCase: MarkCartAsPaidUseCase
) : ViewModel() {
    // State to hold the status of each product detail request
    private val _productDetailState =
        MutableStateFlow<HashMap<Int, ProductDetailState>>(hashMapOf())
    val productDetailState: StateFlow<HashMap<Int, ProductDetailState>> = _productDetailState

    private val _markCartAsPaidState =
        MutableStateFlow<MarkCartAsPaidState>(MarkCartAsPaidState.Idle)
    val markCartAsPaidState: StateFlow<MarkCartAsPaidState> = _markCartAsPaidState


    fun onEvent(event: BillDetailEvent) {
        when (event) {
            is BillDetailEvent.GetProductDetails -> {
                getProductDetails(event.productIds)
            }

            is BillDetailEvent.MarkAsPaid -> {
                markCartAsPaid(event.id)
            }
        }
    }

    private fun markCartAsPaid(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _markCartAsPaidState.update {
                MarkCartAsPaidState.Loading
            }
            delay(2000)
            try {
                val isUpdated = markCartAsPaidUseCase(id)
                _markCartAsPaidState.update {
                    (if (isUpdated) {
                        MarkCartAsPaidState.Success("Bill marked as paid successfully")
                    } else {
                        MarkCartAsPaidState.Error("Failed to mark as paid")
                    })
                }
            } catch (_: Exception) {
                _markCartAsPaidState.update {
                    MarkCartAsPaidState.Error("Failed to mark as paid")
                }
            }

        }
    }

    fun getProductDetails(productIds: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            // Set all products to Loading state
            _productDetailState.update {
                HashMap(productIds.associateWith { ProductDetailState.Loading })
            }

            // Launch parallel async requests
            val deferredResults = productIds.map { productId ->
                async {
                    val result = runCatching { getProductDetailUseCase(productId) }
                    productId to result
                }
            }

            // Handle each result and update state accordingly
            deferredResults.forEach { deferred ->
                val (productId, result) = deferred.await()

                _productDetailState.update { currentState ->
                    val updatedState = currentState.toMutableMap()
                    updatedState[productId] = result.fold(
                        onSuccess = { ProductDetailState.Success(it) },
                        onFailure = {
                            ProductDetailState.Error(
                                "Failed to load product $productId: ${it.message ?: "Unknown error"}"
                            )
                        }
                    )
                    updatedState as HashMap
                }
            }
        }
    }

}