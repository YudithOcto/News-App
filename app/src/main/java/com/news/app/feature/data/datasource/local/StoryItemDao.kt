package com.news.app.feature.data.datasource.local

import androidx.room.*
import com.news.app.feature.domain.model.StoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryItemDao {

    @Query("SELECT * FROM StoryItem")
    fun getStories(): Flow<List<StoryItem>>

    @Query("SELECT * FROM StoryItem WHERE id = :id")
    suspend fun getStoriesById(id: Int): StoryItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(item: StoryItem)

    @Delete
    suspend fun deleteStory(item: StoryItem)
}