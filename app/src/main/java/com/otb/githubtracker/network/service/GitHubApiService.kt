package com.otb.githubtracker.network.service

import com.otb.githubtracker.feature.issues.OpenIssuesModels
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Mohit Rajput on 13/08/22.
 */
interface GitHubApiService {
    @GET("repos/{org_name}/{repo_name}/issues")
    suspend fun fetchIssues(
        @Path("org_name") organizationName: String,
        @Path("repo_name") repositoryName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): Response<List<OpenIssuesModels.IssueResponse>>
}