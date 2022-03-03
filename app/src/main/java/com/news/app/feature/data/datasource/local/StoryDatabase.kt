package com.news.app.feature.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.news.app.feature.domain.model.StoryItem

@Database(
    entities = [StoryItem::class],
    version = 1
)
abstract class StoryDatabase: RoomDatabase() {

    abstract val storyItemDao: StoryItemDao

    companion object {
        const val DATABASE_NAME = "Story Database"
    }
}