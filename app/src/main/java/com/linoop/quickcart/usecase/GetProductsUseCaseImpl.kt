package com.linoop.quickcart.usecase

import com.linoop.quickcart.model.GetProductsResponse
import com.linoop.quickcart.repository.ProductRepository
import com.linoop.quickcart.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class GetProductsUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : GetProductsUseCase {
    override fun invoke(): Flow<Resource<GetProductsResponse>> = repository.invoke()
}