package com.news.app.feature.data.repository

import com.news.app.feature.data.datasource.remote.NewsApi
import com.news.app.feature.data.datasource.remote.StoryItemDto
import com.news.app.feature.domain.repository.StoryRepository
import javax.inject.Inject

class DefaultStoryRepository @Inject constructor(
    private val api: NewsApi
): StoryRepository {

    override suspend fun getStories(): List<Int> {
        return api.getTopStories()
    }

    override suspend fun getStoryDetail(id: Int): StoryItemDto {
        return api.getStoryDetail(id)
    }

}