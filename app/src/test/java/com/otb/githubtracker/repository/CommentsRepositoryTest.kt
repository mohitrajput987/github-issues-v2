package com.otb.githubtracker.repository

import com.otb.githubtracker.db.CommentsDao
import com.otb.githubtracker.feature.IssueCommonModels
import com.otb.githubtracker.feature.comments.CommentsModels
import com.otb.githubtracker.feature.comments.CommentsRepository
import com.otb.githubtracker.network.ApiResult
import com.otb.githubtracker.network.interceptor.NetworkNotAvailableException
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
class CommentsRepositoryTest {

    @MockK
    private lateinit var gitHubApiService: GitHubApiService

    @MockK
    private lateinit var commentsDao: CommentsDao

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
    }

    @Test
    fun `success result should be returned on comments api success`() = runTest {
        val date = Date()
        val user = IssueCommonModels.UserResponse(id = 1, "userName", "avatar url", "admin")
        val response = listOf(
            CommentsModels.CommentResponse(
                id = 1,
                "comment_body",
                "comment_url",
                date,
                date,
                user
            )
        )
        coEvery {
            gitHubApiService.fetchIssueComments("comment_url")
        } returns Response.success(response)

        val repository = CommentsRepository(gitHubApiService, commentsDao)
        val result = repository.fetchComments("comment_url", "issue_url")
        assert(result is ApiResult.Success)
        assertEquals(response, (result as ApiResult.Success).data)
    }

    @Test
    fun `error result should be returned on comments api failure`() = runTest {
        coEvery {
            gitHubApiService.fetchIssueComments("comment_url")
        } returns mockRetrofitErrorResponse()

        val repository = CommentsRepository(gitHubApiService, commentsDao)
        val result = repository.fetchComments("comment_url", "issue_url")
        assert(result is ApiResult.Error)
    }

    @Test
    fun `get data from local db on network issue`() = runTest {
        val date = Date()
        val user = IssueCommonModels.UserResponse(id = 1, "userName", "avatar url", "admin")
        val response = listOf(
            CommentsModels.CommentResponse(
                id = 1,
                "comment_body",
                "issue_url",
                date,
                date,
                user
            )
        )
        coEvery {
            commentsDao.getCommentOfIssue("issue_url")
        } returns response

        coEvery {
            gitHubApiService.fetchIssueComments("comment_url")
        } throws NetworkNotAvailableException()


        val repository = CommentsRepository(gitHubApiService, commentsDao)
        val result = repository.fetchComments("comment_url", "issue_url")
        assert(result is ApiResult.Success)
        assertEquals(response, (result as ApiResult.Success).data)
    }
}
