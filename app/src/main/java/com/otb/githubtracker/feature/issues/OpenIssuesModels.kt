package com.otb.githubtracker.feature.issues

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class OpenIssuesModels {
    data class IssueEntity(
        val id: Long,
        val title: String,
        val description: String,
        val updatedAt: String,
        val user: UserEntity
    )

    data class UserEntity(
        val id: Long,
        val userName: String,
        val imageUrl: String
    )

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

        @SerializedName("created_at")
        val createdAt: Date,

        @SerializedName("closed_at")
        val closedAt: Date?,

        @SerializedName("updated_at")
        val updatedAt: Date,

        @SerializedName("user")
        val user: UserResponse
    )

    @Keep
    data class UserResponse(
        @SerializedName("id")
        val id: Long,

        @SerializedName("login")
        val userName: String,

        @SerializedName("avatar_url")
        val avatarUrl: String,

        @SerializedName("type")
        val type: String,
    )
}