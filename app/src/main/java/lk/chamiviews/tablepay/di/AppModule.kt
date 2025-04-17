package lk.chamiviews.tablepay.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lk.chamiviews.tablepay.data.local.CartDatabase
import lk.chamiviews.tablepay.data.model.LocalCart
import lk.chamiviews.tablepay.data.remote.ApiService
import lk.chamiviews.tablepay.data.repository.CartRemoteMediator
import lk.chamiviews.tablepay.data.repository.CartRepositoryImpl
import lk.chamiviews.tablepay.data.repository.ProductRepositoryImpl
import lk.chamiviews.tablepay.domain.repository.CartRepository
import lk.chamiviews.tablepay.domain.repository.ProductRepository
import lk.chamiviews.tablepay.domain.usecase.GetProductDetailUseCase
import lk.chamiviews.tablepay.domain.usecase.MarkCartAsPaidUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    fun provideRetrofit(): ApiService =
        Retrofit.Builder()
            .client(provideHttpClient())
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

    @Provides
    @Singleton
    fun providePlanetDatabase(@ApplicationContext context: Context): CartDatabase {
        return Room.databaseBuilder(
            context,
            CartDatabase::class.java,
            "cart.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideCartPager(
        cartDatabase: CartDatabase,
        apiService: ApiService
    ): Pager<Int, LocalCart> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = CartRemoteMediator(
                cartDatabase, apiService
            ),
            pagingSourceFactory = {
                cartDatabase.cartDao.getAll()
            }
        )
    }

    @Provides
    fun provideProductRepository(apiService: ApiService): ProductRepository =
        ProductRepositoryImpl(apiService)

    @Provides
    fun provideGetProductDetailUseCase(productRepository: ProductRepository): GetProductDetailUseCase =
        GetProductDetailUseCase(productRepository)

    @Provides
    fun provideCartRepository(cartDatabase: CartDatabase): CartRepository =
        CartRepositoryImpl(cartDatabase)

    @Provides
    fun provideMarkAsPaidUseCase(cartRepository: CartRepository): MarkCartAsPaidUseCase =
        MarkCartAsPaidUseCase(cartRepository)

}