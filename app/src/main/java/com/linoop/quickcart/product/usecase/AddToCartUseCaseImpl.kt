package com.linoop.quickcart.product.usecase

import com.linoop.quickcart.model.Product
import com.linoop.quickcart.product.repository.CartRepository
import com.linoop.quickcart.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class AddToCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : AddToCartUseCase {
    override fun invoke(product: Product): Flow<Resource<Long>> = cartRepository.invoke(product)
}