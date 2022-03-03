package com.news.app.feature.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.app.feature.domain.usecase.GetStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val getStoryUseCase: GetStoryUseCase
): ViewModel()
{
    init {
        getStories()
    }

    private fun getStories() = viewModelScope.launch {
        getStoryUseCase.invoke()
    }
}