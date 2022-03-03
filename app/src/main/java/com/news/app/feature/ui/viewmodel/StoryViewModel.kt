package com.news.app.feature.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.app.feature.data.datasource.local.StoryDatabase
import com.news.app.feature.data.datasource.remote.StoryItemDto
import com.news.app.feature.data.datasource.remote.toStoryItem
import com.news.app.feature.domain.model.StoryItem
import com.news.app.feature.domain.repository.StoryRepository
import com.news.app.feature.domain.usecase.GetStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val getStoryUseCase: GetStoryUseCase,
    private val repository: StoryRepository,
    private val storyDatabase: StoryDatabase
): ViewModel()
{

    private var _offset = 0
    private val _storyIds = arrayListOf<Int>()
    private val _stories = arrayListOf<StoryItemDto>()

    val stories: MutableState<State> = mutableStateOf(State.Loading)

    fun getStories() = viewModelScope.launch {
        if (_storyIds.isEmpty()) {
            val storyIds = repository.getStories()
            _storyIds.addAll(storyIds)
        }

        stories.value = State.Loading

        try {
            _storyIds
                .subList(_offset, _storyIds.size)
                .take(LIMIT)
                .forEach { id ->
                    val getStoryDetail = async { repository.getStoryDetail(id) }
                    val convertedStory = getStoryDetail.await()
                    _stories.add(convertedStory)
                    addToDb(convertedStory.toStoryItem())
                }

            _offset += LIMIT
            stories.value = State.SuccessGetData(_stories)
        } catch (e: Exception) {
          stories.value = State.Error(e.message.orEmpty())
        }
    }

    private suspend fun addToDb(item: StoryItem) {
        storyDatabase.storyItemDao.insertStory(item)
    }

    companion object {
        const val LIMIT = 20
    }

    sealed class State {
        object Loading: State()
        data class Error(val title: String): State()
        data class SuccessGetData(val data: List<StoryItemDto>): State()
    }
}