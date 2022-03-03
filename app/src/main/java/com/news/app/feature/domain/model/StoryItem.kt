package com.news.app.feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class StoryItem(
    @PrimaryKey val id: Int,
    val title: String,
    val time: String
)