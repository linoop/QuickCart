package com.linoop.quickcart.product.usecase

import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.product.repository.ProductRepository
import com.linoop.quickcart.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class GetProductByIdUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : GetProductByIdUseCase {
    override fun invoke(productId: Int): Flow<Resource<Product>> = repository.invoke(productId)
}