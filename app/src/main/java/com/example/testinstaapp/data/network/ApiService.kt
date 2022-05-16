package com.example.testinstaapp.data.network

import com.example.testinstaapp.BuildConfig.API_URL
import com.example.testinstaapp.data.model.InstaPost
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("{photos}")
    suspend fun getPostsList(
        @Path("photos") photos: String,
        @Query("_page") _page: Int,
        @Query("_limit") _limit: Int
    ): List<InstaPost>

    companion object {
// you can manage loading distance with
// MAX_PAGE_SIZE as number of posts that have to be loaded on one page
// LAST_POST_NUMBER as number of all posts
// MAX_LOAD_SIZE as number of posts that have to be saved in temporary memory
// PREF_FETCH_DISTANCE as boundary of loading process (e.g. number of post at which loading of next page starts

        const val MAX_PAGE_SIZE = 50
        const val LAST_POST_NUMBER = 5000
        const val MAX_LOAD_SIZE = 200
        const val PREF_FETCH_DISTANCE = 20

        operator fun invoke(): ApiService {
            return Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}