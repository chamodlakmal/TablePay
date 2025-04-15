package lk.chamiviews.tablepay.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import lk.chamiviews.tablepay.data.model.LocalCart
import lk.chamiviews.tablepay.data.model.toDomain
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    pager: Pager<Int, LocalCart>
) : ViewModel() {

    val cartPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toDomain() }
        }
        .cachedIn(viewModelScope)

}