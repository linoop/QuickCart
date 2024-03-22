package com.linoop.quickcart.usecase

import androidx.paging.PagingData
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.repository.PagProductRepo
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class PagProductUseCaseImpl @Inject constructor(
    private val pagProductRepo: PagProductRepo
) : PagProductUseCase {
    override suspend fun invoke(): Flow<PagingData<Product>> = pagProductRepo.invoke()
}