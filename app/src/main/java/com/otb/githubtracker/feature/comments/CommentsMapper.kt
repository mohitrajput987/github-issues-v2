package com.otb.githubtracker.feature.comments

import com.otb.githubtracker.common.base.Mapper
import com.otb.githubtracker.feature.IssueCommonModels
import com.otb.githubtracker.util.DateUtils

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class CommentsMapper :
    Mapper<List<CommentsModels.CommentResponse>, List<CommentsModels.CommentEntity>> {
    override fun mapFrom(from: List<CommentsModels.CommentResponse>): List<CommentsModels.CommentEntity> {
        return from.map {
            val user = IssueCommonModels.UserEntity(it.user.id, it.user.userName, it.user.avatarUrl)
            CommentsModels.CommentEntity(
                id = it.id,
                description = it.body,
                commentsUrl = it.url,
                updatedAt = DateUtils.getFormattedTime(it.createdAt),
                user = user
            )
        }
    }
}
