package com.example.community.di.module.data

import com.example.data.api.story.*
import com.example.data.datasource.story.datasource.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoryDataSourceModule {
    @Provides
    @Singleton
    fun provideCreateStoryDataSource(service: CreateStoryService): CreateStoryDataSourceImpl {
        return CreateStoryDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideGetStoryDataSource(service: GetStoryService): GetStoryDataSourceImpl {
        return GetStoryDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideGetSingleStoryDataSource(service: GetStorySingleService): GetStorySingleDataSourceImpl {
        return GetStorySingleDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideModifyStoryDataSource(service: ModifyStoryService): ModifyStoryDataSourceImpl {
        return ModifyStoryDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideDeleteStoryDataSource(service: DeleteStoryService): DeleteStoryDataSourceImpl {
        return DeleteStoryDataSourceImpl(service)
    }
}