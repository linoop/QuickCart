package com.linoop.quickcart.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.network.ApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class PagProductRepoImpl @Inject constructor(
    private val apiService: ApiService,
) : PagProductRepo {
    override fun invoke(): Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = 100, prefetchDistance = 2),
        pagingSourceFactory = { ProductPagingSource(apiService) }
    ).flow
}

class ProductPagingSource(private val apiService: ApiService) : PagingSource<Int, Product>() {

    ///@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getProducts(
                10, currentPage
                /*  apiKey = Constants.MOVIE_API_KEY,
                  pageNumber = currentPage*/
            )
            LoadResult.Page(
                data = response.body()?.products ?: listOf(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.body()?.products!!.isEmpty()) null else response.body()?.skip!! + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

}