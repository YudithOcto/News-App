package com.news.app.feature.domain.usecase

import com.news.app.feature.data.datasource.remote.toStoryItem
import com.news.app.feature.domain.model.StoryItem
import com.news.app.feature.domain.repository.StoryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*

class GetStoryUseCase(
    private val repository: StoryRepository
) {

    operator fun invoke(): Flow<List<StoryItem>> = flow {
        val storyIds = repository.getStories()
        coroutineScope {
            val stories = storyIds.map { id ->
                val getStoryDetail = async { repository.getStoryDetail(id) }
                getStoryDetail.await()
            }
            emit(stories.map { it.toStoryItem() })
        }
    }
}