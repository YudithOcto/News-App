package com.news.app.feature.di

import android.app.Application
import androidx.room.Room
import com.news.app.feature.data.datasource.local.StoryDatabase
import com.news.app.feature.data.datasource.remote.NewsApi
import com.news.app.feature.data.repository.DefaultStoryRepository
import com.news.app.feature.domain.repository.StoryRepository
import com.news.app.feature.domain.usecase.GetStoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStoryDatabase(app: Application): StoryDatabase {
        return Room.databaseBuilder(
            app,
            StoryDatabase::class.java,
            StoryDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideStoryRepository(api: NewsApi): StoryRepository {
        return DefaultStoryRepository(api)
    }

    @Provides
    @Singleton
    fun provideStoryUseCase(repository: StoryRepository): GetStoryUseCase {
        return GetStoryUseCase(repository)
    }
}