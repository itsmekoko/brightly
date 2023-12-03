package com.kokodeco.brightlyapp.data.remote

import com.kokodeco.brightlyapp.data.remote.dto.NewsResponse
import com.kokodeco.brightlyapp.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface BrightlyApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY

    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}
