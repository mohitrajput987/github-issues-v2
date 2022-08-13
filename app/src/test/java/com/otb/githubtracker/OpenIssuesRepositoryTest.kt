package com.otb.githubtracker

import com.otb.githubtracker.feature.issues.OpenIssuesModels
import com.otb.githubtracker.feature.issues.OpenIssuesRepository
import com.otb.githubtracker.network.ApiResult
import com.otb.githubtracker.network.service.GitHubApiService
import com.otb.githubtracker.util.mockRetrofitErrorResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import java.util.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class OpenIssuesRepositoryTest {

    @MockK
    private lateinit var gitHubApiService: GitHubApiService

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
    }

    @Test
    fun `success result should be returned on issues api success`() = runTest {
        val date = Date()
        val user = OpenIssuesModels.UserResponse(id = 1, "userName", "avatar url", "admin")
        val issuesResponse = listOf(
            OpenIssuesModels.IssueResponse(
                id = 1,
                "title",
                "body",
                "url",
                date,
                date,
                date,
                user
            )
        )
        coEvery {
            gitHubApiService.fetchIssues(any(), any(), any(), any())
        } returns Response.success(issuesResponse)

        val repository = OpenIssuesRepository(gitHubApiService)
        val result = repository.fetchIssues(
            organizationName = "mohitrajput987",
            repositoryName = "github-issues-v2",
            page = 1
        )
        assert(result is ApiResult.Success)
        assertEquals(issuesResponse, (result as ApiResult.Success).data)
    }

    @Test
    fun `error result should be returned on issues api failure`() = runTest {
        coEvery {
            gitHubApiService.fetchIssues(any(), any(), any(), any())
        } returns mockRetrofitErrorResponse()

        val repository = OpenIssuesRepository(gitHubApiService)
        val result = repository.fetchIssues(
            organizationName = "",
            repositoryName = "github-issues-v2",
            page = 0
        )
        assert(result is ApiResult.Error)
    }
}
