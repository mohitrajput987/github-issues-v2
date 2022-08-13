package com.otb.githubtracker.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

fun <T> mockRetrofitErrorResponse(): Response<T> {
    val errorResponse = """
            {"code":"404"}
        """.trimIndent()
    val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
    return Response.error(404, errorResponseBody)
}

fun mockRetrofitResponseBody(): ResponseBody {
    val errorResponse = """
            {"code":"404"}
        """.trimIndent()
    return errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
}

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
