package com.otb.githubtracker.feature.issues

import com.otb.githubtracker.network.ApiResult

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class OpenIssuesContact {
    interface Repository{
        suspend fun fetchIssues(organizationName: String, repositoryName: String, page: Int) : ApiResult<List<OpenIssuesModels.IssueResponse>>
    }
}