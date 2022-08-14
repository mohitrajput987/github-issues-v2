package com.otb.githubtracker.db

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by Mohit Rajput on 13/08/22.
 */

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

    @Provides
    fun providesCommentsDao(appDatabase: AppDatabase) = appDatabase.commentsDao()

    @Provides
    fun providesIssuesDao(appDatabase: AppDatabase) = appDatabase.issuesDao()
}