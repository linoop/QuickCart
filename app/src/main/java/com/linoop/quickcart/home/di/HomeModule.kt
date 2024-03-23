package com.linoop.quickcart.home.di

import com.linoop.quickcart.home.repository.ProductListRepo
import com.linoop.quickcart.home.repository.ProductListRepoImpl
import com.linoop.quickcart.home.usecase.GetProductsUseCase
import com.linoop.quickcart.home.usecase.GetProductsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindProductListRepo(repo: ProductListRepoImpl): ProductListRepo

    @Binds
    abstract fun bindGetProductsUseCase(useCase: GetProductsUseCaseImpl): GetProductsUseCase
}