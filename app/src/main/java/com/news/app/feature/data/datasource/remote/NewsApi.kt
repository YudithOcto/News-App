package com.news.app.feature.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {

    @GET("topstories.json?print=pretty")
    suspend fun getTopStories(): List<Int>

    @GET("item/{id}.json?print=pretty")
    suspend fun getStoryDetail(@Path("id") id: Int): StoryItemDto
}