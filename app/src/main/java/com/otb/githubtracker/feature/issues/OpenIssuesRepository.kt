package com.otb.githubtracker.feature.issues

import com.otb.githubtracker.db.IssuesDao
import com.otb.githubtracker.network.ApiResult
import com.otb.githubtracker.network.ErrorType
import com.otb.githubtracker.network.getResult
import com.otb.githubtracker.network.service.GitHubApiService
import javax.inject.Inject

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class OpenIssuesRepository @Inject constructor(
    private val gitHubApiService: GitHubApiService,
    private val issuesDao: IssuesDao
) : OpenIssuesContact.Repository {

    override suspend fun fetchIssues(
        organizationName: String,
        repositoryName: String,
        page: Int
    ): ApiResult<List<OpenIssuesModels.IssueResponse>> {
        val result =
            getResult { gitHubApiService.fetchIssues(organizationName, repositoryName, page) }
        if (result is ApiResult.Success) {
            issuesDao.clear()
            issuesDao.insertAll(result.data)
        } else if (result is ApiResult.Error && result.type == ErrorType.NetworkException) {
            val issues = issuesDao.getAllIssues()
            if (issues.isNotEmpty()) {
                return ApiResult.Success(issues)
            }
        }

        return result
    }
}