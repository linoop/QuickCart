package com.linoop.quickcart.product.di

import com.linoop.quickcart.product.repository.ProductRepository
import com.linoop.quickcart.product.repository.ProductRepositoryImpl
import com.linoop.quickcart.product.usecase.GetProductByIdUseCase
import com.linoop.quickcart.product.usecase.GetProductByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProductModule {
    @Binds
    abstract fun bindProductRepository(repo: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindGetProductByIdUseCase(useCase: GetProductByIdUseCaseImpl): GetProductByIdUseCase

}