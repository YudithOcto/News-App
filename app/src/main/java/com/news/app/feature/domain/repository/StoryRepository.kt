package com.news.app.feature.domain.repository

import com.news.app.feature.data.datasource.remote.StoryItemDto

interface StoryRepository {

    suspend fun getStories(): List<Int>

    suspend fun getStoryDetail(id: Int): StoryItemDto
}