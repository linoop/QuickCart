package com.linoop.quickcart.cart.usecase

import com.linoop.quickcart.cart.repository.CartRepository
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class OpenCartUseCaseImpl @Inject constructor(
    private val repository: CartRepository
) : OpenCartUseCase {
    override suspend fun invoke(): Flow<Resource<List<Product>>> = repository.getAllItems()
}