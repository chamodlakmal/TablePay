package lk.chamiviews.tablepay.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import lk.chamiviews.tablepay.data.local.CartDatabase
import lk.chamiviews.tablepay.data.model.LocalCart
import lk.chamiviews.tablepay.data.mapper.toLocalCart
import lk.chamiviews.tablepay.data.remote.ApiService

@OptIn(ExperimentalPagingApi::class)
class CartRemoteMediator(
    private val cartDatabase: CartDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, LocalCart>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalCart>
    ): MediatorResult {
        return try {

            val skip = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 0
                    else lastItem.id + 1
                }
                else -> return MediatorResult.Success(true)
            }
            val response = apiService.getCarts(state.config.pageSize, skip)
            if (loadType == LoadType.REFRESH) cartDatabase.cartDao.clearAll()
            cartDatabase.cartDao.insertAll(response.carts.map { it.toLocalCart() })
            MediatorResult.Success(endOfPaginationReached = response.carts.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}