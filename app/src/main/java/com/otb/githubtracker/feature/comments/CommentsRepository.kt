package com.otb.githubtracker.feature.comments

import com.otb.githubtracker.db.CommentsDao
import com.otb.githubtracker.network.ApiResult
import com.otb.githubtracker.network.ErrorType
import com.otb.githubtracker.network.getResult
import com.otb.githubtracker.network.service.GitHubApiService
import javax.inject.Inject

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class CommentsRepository @Inject constructor(
    private val gitHubApiService: GitHubApiService,
    private val commentsDao: CommentsDao
) :
    CommentsContact.Repository {

    override suspend fun fetchComments(
        commentsUrl: String,
        issueUrl: String
    ): ApiResult<List<CommentsModels.CommentResponse>> {
        val result =
            getResult { gitHubApiService.fetchIssueComments(commentsUrl) }
        if (result is ApiResult.Success) {
            commentsDao.deleteCommentsOfIssue(issueUrl)
            commentsDao.insertAll(result.data)
        } else if (result is ApiResult.Error && result.type == ErrorType.NetworkException) {
            val comments = commentsDao.getCommentOfIssue(issueUrl)
            if (comments.isNotEmpty()) {
                return ApiResult.Success(comments)
            }
        }

        return result
    }
}