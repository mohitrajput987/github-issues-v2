package com.otb.githubtracker.feature.comments

import com.otb.githubtracker.network.ApiResult

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class CommentsContact {
    interface Repository {
        suspend fun fetchComments(commentsUrl: String, issueUrl : String): ApiResult<List<CommentsModels.CommentResponse>>
    }
}