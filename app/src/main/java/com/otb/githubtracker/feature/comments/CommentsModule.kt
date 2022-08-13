package com.otb.githubtracker.feature.comments

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

/**
 * Created by Mohit Rajput on 13/08/22.
 */

@Module
@InstallIn(ActivityRetainedComponent::class)
interface CommentsModule {
    @Binds
    fun bindCommentsRepository(commentsRepository: CommentsRepository): CommentsContact.Repository
}