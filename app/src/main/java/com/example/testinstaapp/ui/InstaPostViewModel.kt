package com.example.testinstaapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.*
import com.example.testinstaapp.BuildConfig.QUERY
import com.example.testinstaapp.data.model.InstaPost
import com.example.testinstaapp.data.network.ApiService
import com.example.testinstaapp.data.network.InstaPostsPagingSource

class InstaPostViewModel : ViewModel() {
    private var apiService: ApiService = ApiService.invoke()

    fun getPosts()
            : Flow<PagingData<InstaPost>> {
        return Pager(config = PagingConfig(
            pageSize = ApiService.MAX_PAGE_SIZE,
            maxSize = ApiService.MAX_LOAD_SIZE,
            prefetchDistance = ApiService.PREF_FETCH_DISTANCE,
            enablePlaceholders = true
        ),
            pagingSourceFactory = { InstaPostsPagingSource(apiService, QUERY) }).flow.cachedIn(
            viewModelScope
        )
    }
}