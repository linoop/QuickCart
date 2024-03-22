package com.linoop.quickcart.di

import com.linoop.quickcart.repository.ProductRepositoryImpl
import com.linoop.quickcart.network.ApiService
import com.linoop.quickcart.repository.PagProductRepo
import com.linoop.quickcart.repository.PagProductRepoImpl
import com.linoop.quickcart.repository.ProductRepository
import com.linoop.quickcart.usecase.GetProductsUseCase
import com.linoop.quickcart.usecase.GetProductsUseCaseImpl
import com.linoop.quickcart.usecase.PagProductUseCase
import com.linoop.quickcart.usecase.PagProductUseCaseImpl
import com.linoop.quickcart.utils.Constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService(@Named("Retrofit") okHttpClient: OkHttpClient): ApiService = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    @Named("Retrofit")
    fun provideOkHttpClientForRetrofit(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProductModule {
    @Binds
    abstract fun bindRepository(myRepository: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindUseCase(useCase: GetProductsUseCaseImpl): GetProductsUseCase

    @Binds
    abstract fun bindPagProductUseCase(useCase: PagProductUseCaseImpl): PagProductUseCase

    @Binds
    abstract fun bindPagProductRepo(useCase: PagProductRepoImpl): PagProductRepo
}