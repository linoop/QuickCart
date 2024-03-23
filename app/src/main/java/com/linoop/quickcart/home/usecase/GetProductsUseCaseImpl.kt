package com.linoop.quickcart.home.usecase

import androidx.paging.PagingData
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.home.repository.ProductListRepo
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class GetProductsUseCaseImpl @Inject constructor(
    private val productListRepo: ProductListRepo
) : GetProductsUseCase {
    override suspend fun invoke(): Flow<PagingData<Product>> = productListRepo.invoke()
}