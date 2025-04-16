package lk.chamiviews.tablepay.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import lk.chamiviews.tablepay.domain.usecase.GetProductDetailUseCase
import lk.chamiviews.tablepay.presentation.events.BillDetailEvent
import lk.chamiviews.tablepay.presentation.state.ProductDetailState
import javax.inject.Inject

@HiltViewModel
class BillDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {
    private val _productDetailState =
        MutableStateFlow<ProductDetailState>(ProductDetailState.Loading)
    val productDetailState: StateFlow<ProductDetailState> = _productDetailState

    fun onEvent(event: BillDetailEvent) {
        when (event) {
            is BillDetailEvent.GetProductDetails -> {
                getProductDetails(event.productIds)
            }
        }
    }

    fun getProductDetails(productIds: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            _productDetailState.value = ProductDetailState.Loading

            val deferredResults = productIds.map { productId ->
                async {
                    runCatching { getProductDetailUseCase(productId) }
                }
            }

            val results = deferredResults.awaitAll()

            val successList = results.mapNotNull { it.getOrNull() }
            val errorMessages = results.mapNotNull { it.exceptionOrNull()?.message }

            _productDetailState.value = when {
                successList.isNotEmpty() && errorMessages.isEmpty() -> {
                    ProductDetailState.Success(successList)
                }

                errorMessages.isNotEmpty() -> {
                    ProductDetailState.Error(
                        "Some products failed to load:\n" + errorMessages.joinToString("\n")
                    )
                }

                else -> {
                    ProductDetailState.Error(
                        "Failed to load products:\n" + errorMessages.joinToString("\n")
                    )
                }
            }
        }
    }
}