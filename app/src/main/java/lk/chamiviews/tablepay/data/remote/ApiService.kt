package lk.chamiviews.tablepay.data.remote

import lk.chamiviews.tablepay.data.model.CartResponse
import lk.chamiviews.tablepay.data.model.ProductDetailDto
import lk.chamiviews.tablepay.data.model.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("carts")
    suspend fun getCarts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): CartResponse

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductDetailDto
}