package com.otb.githubtracker.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.otb.githubtracker.feature.issues.OpenIssuesContact
import com.otb.githubtracker.feature.issues.OpenIssuesMapper
import com.otb.githubtracker.feature.issues.OpenIssuesViewModel
import com.otb.githubtracker.network.ApiResult
import com.otb.githubtracker.network.ErrorType
import com.otb.githubtracker.network.ViewState
import com.otb.githubtracker.util.getOrAwaitValue
import com.otb.githubtracker.util.mockCoroutinesDispatcherProvider
import com.otb.githubtracker.util.mockIssueResponse
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
class OpenIssuesViewModelTest {
    @get:Rule
    val liveDataRule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: OpenIssuesContact.Repository

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
    }

    @Test
    fun `fetch issues - loading then success`() {
        val response = mockIssueResponse()
        coEvery {
            repository.fetchIssues("mohitrajput987", "github-issues-v2", 1)
        } returns ApiResult.Success(response)

        val issueEntities = OpenIssuesMapper().mapFrom(response)
        val viewModel = OpenIssuesViewModel(
            repository,
            mockCoroutinesDispatcherProvider(testCoroutineDispatcher)
        )

        testCoroutineDispatcher.pauseDispatcher()
        viewModel.fetchIssues("mohitrajput987", "github-issues-v2")

        val viewState = viewModel.openIssuesLiveData.getOrAwaitValue()
        assert(viewState is ViewState.Loading)

        testCoroutineDispatcher.resumeDispatcher()
        viewModel.openIssuesLiveData.observeForever {
            assertEquals(it, ViewState.Success(issueEntities))
        }
    }

    @Test
    fun `fetch issues - loading then error`() {
        coEvery {
            repository.fetchIssues("mohitrajput987", "github-issues-v2", 1)
        } returns ApiResult.Error("some error message", ErrorType.InvalidData)

        val viewModel = OpenIssuesViewModel(
            repository,
            mockCoroutinesDispatcherProvider(testCoroutineDispatcher)
        )

        testCoroutineDispatcher.pauseDispatcher()
        viewModel.fetchIssues("mohitrajput987", "github-issues-v2")

        val viewState = viewModel.openIssuesLiveData.getOrAwaitValue()
        assert(viewState is ViewState.Loading)

        testCoroutineDispatcher.resumeDispatcher()
        viewModel.openIssuesLiveData.observeForever {
            assertEquals(it, ViewState.Error("some error message"))
        }
    }
}