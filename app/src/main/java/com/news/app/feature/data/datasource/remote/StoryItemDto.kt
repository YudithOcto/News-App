package com.news.app.feature.data.datasource.remote

import androidx.annotation.Keep
import com.news.app.feature.domain.model.StoryItem
import java.text.SimpleDateFormat
import java.util.*

@Keep
data class StoryItemDto(
    val id: Int,
    val title: String,
    val kids: List<Int>,
    val by: String,
    val time: Long
)

fun StoryItemDto.toStoryItem(): StoryItem {
    return StoryItem(
        id = id,
        title = title,
        time = getDate(time)
    )
}

fun getDate(timestamp: Long) :String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    val date = Date(timestamp * 1000)
    return formatter.format(date)
}