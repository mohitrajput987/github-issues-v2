package com.otb.githubtracker.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.otb.githubtracker.common.CoroutinesDispatcherProvider
import com.otb.githubtracker.feature.IssueCommonModels
import com.otb.githubtracker.feature.comments.CommentsModels
import com.otb.githubtracker.feature.issues.OpenIssuesModels
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

fun <T> mockRetrofitErrorResponse(): Response<T> {
    val errorResponse = """
            {"code":"404"}
        """.trimIndent()
    val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
    return Response.error(404, errorResponseBody)
}

fun mockIssueResponse(): List<OpenIssuesModels.IssueResponse> {
    val date = Date()
    val user = IssueCommonModels.UserResponse(id = 1, "userName", "avatar url", "admin")
    return listOf(
        OpenIssuesModels.IssueResponse(
            id = 1,
            "title",
            "body",
            "url",
            "comment_url",
            date,
            date,
            date,
            user
        )
    )
}

fun mockCommentResponse(): List<CommentsModels.CommentResponse> {
    val date = Date()
    val user = IssueCommonModels.UserResponse(id = 1, "userName", "avatar url", "admin")
    return listOf(
        CommentsModels.CommentResponse(
            id = 1,
            "comment_body",
            "issue_url",
            date,
            date,
            user
        )
    )
}

fun mockIssueEntity() = OpenIssuesModels.IssueEntity(
    1,
    "title",
    "description",
    "comment_url",
    "issue_url",
    "updated_at",
    IssueCommonModels.UserEntity(
        1, "user_name", "image_url"
    )
)

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

@ExperimentalCoroutinesApi
fun mockCoroutinesDispatcherProvider(
    dispatcher: TestCoroutineDispatcher? = null
): CoroutinesDispatcherProvider {
    val sharedTestCoroutineDispatcher = TestCoroutineDispatcher()
    return CoroutinesDispatcherProvider(
        dispatcher ?: sharedTestCoroutineDispatcher,
        dispatcher ?: sharedTestCoroutineDispatcher,
        dispatcher ?: sharedTestCoroutineDispatcher
    )
}
