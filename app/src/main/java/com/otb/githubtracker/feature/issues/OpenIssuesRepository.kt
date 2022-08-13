package com.otb.githubtracker.feature.issues

import com.otb.githubtracker.network.ApiResult
import com.otb.githubtracker.network.getResult
import com.otb.githubtracker.network.service.GitHubApiService
import javax.inject.Inject

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class OpenIssuesRepository @Inject constructor(private val gitHubApiService: GitHubApiService) :
    OpenIssuesContact.Repository {

    override suspend fun fetchIssues(
        organizationName: String,
        repositoryName: String,
        page: Int
    ): ApiResult<List<OpenIssuesModels.IssueResponse>> {
        return getResult {
            gitHubApiService.fetchIssues(
                organizationName, repositoryName, page
            )
        }
    }
}