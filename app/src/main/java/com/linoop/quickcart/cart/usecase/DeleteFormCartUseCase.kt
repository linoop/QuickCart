package com.linoop.quickcart.cart.usecase

import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.flow.Flow

fun interface DeleteFormCartUseCase {
   suspend operator fun invoke(product: Product): Flow<Resource<Boolean>>
}