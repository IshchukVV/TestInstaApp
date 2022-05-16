package com.example.testinstaapp.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testinstaapp.data.model.InstaPost

class InstaPostsPagingSource(
    private val apiService: ApiService,
    private val query: String,
) :
    PagingSource<Int, InstaPost>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, InstaPost> {
        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }else{
            try {
                val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
                val pageSize = params.loadSize.coerceAtMost(ApiService.MAX_PAGE_SIZE)
                val response = apiService.getPostsList(
                    query, pageNumber, pageSize
                )
                return if (!response.isNullOrEmpty()) {
                    val nextPageNumber = if (response.last().id == ApiService.LAST_POST_NUMBER) null else pageNumber + 1
                    val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                    LoadResult.Page(response, prevPageNumber, nextPageNumber)

                }else{
                    LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
                }

            } catch (e: Exception) {
                return LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, InstaPost>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    companion object{
        private const val INITIAL_PAGE_NUMBER = 1
    }
}