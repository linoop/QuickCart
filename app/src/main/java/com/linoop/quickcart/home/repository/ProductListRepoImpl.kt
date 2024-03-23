package com.linoop.quickcart.home.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.network.ApiService
import com.linoop.quickcart.utils.Constants.MAX_PAGE_SIZE
import com.linoop.quickcart.utils.Constants.STARTING_PAGE_INDEX
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class ProductListRepoImpl @Inject constructor(
    private val apiService: ApiService,
) : ProductListRepo {
    override fun invoke(): Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = MAX_PAGE_SIZE, prefetchDistance = 2),
        pagingSourceFactory = { ProductPagingSource(apiService) }
    ).flow
}

class ProductPagingSource(private val apiService: ApiService) : PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            delay(1000)
            apiService.getProducts(10, currentPage).body().let { resp ->
                LoadResult.Page(
                    data = resp?.products ?: listOf(),
                    prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - MAX_PAGE_SIZE,
                    nextKey = if (resp?.products!!.isEmpty()) null else resp.skip + MAX_PAGE_SIZE
                )
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

}