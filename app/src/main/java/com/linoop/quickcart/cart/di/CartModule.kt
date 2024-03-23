package com.linoop.quickcart.cart.di

import com.linoop.quickcart.cart.repository.CartRepository
import com.linoop.quickcart.cart.repository.CartRepositoryImpl
import com.linoop.quickcart.cart.usecase.DeleteFormCartUseCase
import com.linoop.quickcart.cart.usecase.DeleteFormCartUseCaseImpl
import com.linoop.quickcart.cart.usecase.OpenCartUseCase
import com.linoop.quickcart.cart.usecase.OpenCartUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CartModule {
    @Binds
    abstract fun bindCartRepository(cartRepository: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindGetFromCartUseCase(useCase: OpenCartUseCaseImpl): OpenCartUseCase

    @Binds
    abstract fun bindDeleteFormCartUseCase(useCase: DeleteFormCartUseCaseImpl): DeleteFormCartUseCase
}