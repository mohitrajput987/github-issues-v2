package com.otb.githubtracker.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.otb.githubtracker.feature.comments.CommentsActivity
import com.otb.githubtracker.feature.comments.CommentsContact
import com.otb.githubtracker.feature.comments.CommentsMapper
import com.otb.githubtracker.feature.comments.CommentsViewModel
import com.otb.githubtracker.network.ApiResult
import com.otb.githubtracker.network.ErrorType
import com.otb.githubtracker.network.ViewState
import com.otb.githubtracker.util.getOrAwaitValue
import com.otb.githubtracker.util.mockCommentResponse
import com.otb.githubtracker.util.mockCoroutinesDispatcherProvider
import com.otb.githubtracker.util.mockIssueEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * Created by Mohit Rajput on 14/08/22.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class CommentsViewModelTest {
    @get:Rule
    val liveDataRule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: CommentsContact.Repository

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
    }

    @Test
    fun `fetch comments - loading then success`() {
        val response = mockCommentResponse()
        coEvery {
            repository.fetchComments("comment_url", "issue_url")
        } returns ApiResult.Success(response)
        val issueEntity = mockIssueEntity()
        val handle = SavedStateHandle().apply {
            set(CommentsActivity.ISSUE_ENTITY, issueEntity)
        }
        val commentEntities = CommentsMapper().mapFrom(response)
        val viewModel = CommentsViewModel(
            handle,
            repository,
            mockCoroutinesDispatcherProvider(testCoroutineDispatcher)
        )

        testCoroutineDispatcher.pauseDispatcher()
        viewModel.fetchComments()

        val viewState = viewModel.commentsLiveData.getOrAwaitValue()
        assert(viewState is ViewState.Loading)

        testCoroutineDispatcher.resumeDispatcher()
        viewModel.commentsLiveData.observeForever {
            assertEquals(it, ViewState.Success(commentEntities))
        }
    }

    @Test
    fun `fetch issues - loading then error`() {
        coEvery {
            repository.fetchComments("comment_url", "issue_url")
        } returns ApiResult.Error("some error message", ErrorType.InvalidData)

        val issueEntity = mockIssueEntity()
        val handle = SavedStateHandle().apply {
            set(CommentsActivity.ISSUE_ENTITY, issueEntity)
        }

        val viewModel = CommentsViewModel(
            handle,
            repository,
            mockCoroutinesDispatcherProvider(testCoroutineDispatcher)
        )

        testCoroutineDispatcher.pauseDispatcher()
        viewModel.fetchComments()

        val viewState = viewModel.commentsLiveData.getOrAwaitValue()
        assert(viewState is ViewState.Loading)

        testCoroutineDispatcher.resumeDispatcher()
        viewModel.commentsLiveData.observeForever {
            assertEquals(it, ViewState.Error("some error message"))
        }
    }
}