package com.otb.githubtracker.feature.issues

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.otb.githubtracker.feature.IssueCommonModels
import java.util.*
import kotlinx.parcelize.Parcelize

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class OpenIssuesModels {
    @Parcelize
    data class IssueEntity(
        val id: Long,
        val title: String,
        val description: String,
        val commentsUrl: String,
        val updatedAt: String,
        val user: IssueCommonModels.UserEntity
    ) : Parcelable

    @Keep
    data class IssueResponse(
        @SerializedName("id")
        val id: Long,

        @SerializedName("title")
        val title: String,

        @SerializedName("body")
        val body: String,

        @SerializedName("url")
        val url: String,

        @SerializedName("comments_url")
        val commentsUrl: String,

        @SerializedName("created_at")
        val createdAt: Date,

        @SerializedName("closed_at")
        val closedAt: Date?,

        @SerializedName("updated_at")
        val updatedAt: Date,

        @SerializedName("user")
        val user: IssueCommonModels.UserResponse
    )
}