package lk.chamiviews.tablepay.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    // State to hold the status of each product detail request
    private val _productDetailState =
        MutableStateFlow<HashMap<Int, ProductDetailState>>(hashMapOf())
    val productDetailState: StateFlow<HashMap<Int, ProductDetailState>> = _productDetailState


    fun onEvent(event: BillDetailEvent) {
        when (event) {
            is BillDetailEvent.GetProductDetails -> {
                getProductDetails(event.productIds)
            }
        }
    }

    fun getProductDetails(productIds: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            // Initialize a HashMap with 'Loading' for all products
            val initialState = HashMap<Int, ProductDetailState>()
            productIds.forEach { productId ->
                initialState[productId] = ProductDetailState.Loading
            }
            _productDetailState.value = initialState

            // Create a list of async tasks to fetch product details in parallel
            val deferredResults = productIds.map { productId ->
                async {
                    // Wrap each call with runCatching to handle success and failure
                    val result = runCatching { getProductDetailUseCase(productId) }
                    productId to result // Return productId and its result
                }
            }

            // Process each result as it arrives
            deferredResults.forEach { deferred ->
                val (productId, result) = deferred.await()
                result.onSuccess { productDetail ->
                    // As soon as a request is successful, update the state for this product
                    _productDetailState.value = _productDetailState.value.toMutableMap().apply {
                        put(productId, ProductDetailState.Success(productDetail))
                    } as HashMap<Int, ProductDetailState>
                }.onFailure { exception ->
                    // If a request fails, update the state with an error message for this product
                    val errorMessage = exception.message ?: "Unknown error"
                    _productDetailState.value = _productDetailState.value.toMutableMap().apply {
                        put(
                            productId,
                            ProductDetailState.Error("Failed to load product $productId: $errorMessage")
                        )
                    } as HashMap<Int, ProductDetailState>
                }
            }
        }
    }
}