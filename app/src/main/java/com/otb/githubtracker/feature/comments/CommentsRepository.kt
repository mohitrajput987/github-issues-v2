package com.otb.githubtracker.feature.comments

import com.otb.githubtracker.network.ApiResult
import com.otb.githubtracker.network.getResult
import com.otb.githubtracker.network.service.GitHubApiService
import javax.inject.Inject

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class CommentsRepository @Inject constructor(private val gitHubApiService: GitHubApiService) :
    CommentsContact.Repository {

    override suspend fun fetchComments(commentsUrl: String): ApiResult<List<CommentsModels.CommentResponse>> {
        return getResult {
            gitHubApiService.fetchIssueComments(commentsUrl)
        }
    }
}