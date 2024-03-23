package com.linoop.quickcart.cart.usecase

import com.linoop.quickcart.cart.repository.CartRepository
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class DeleteFormCartUseCaseImpl @Inject constructor(
    private val repository: CartRepository
) : DeleteFormCartUseCase {
    override suspend fun invoke(product: Product): Flow<Resource<Boolean>> = repository.deleteItem(product)
}