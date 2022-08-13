package com.otb.githubtracker.feature.comments

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.otb.githubtracker.feature.IssueCommonModels
import java.util.*
import kotlinx.parcelize.Parcelize

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class CommentsModels {
    @Parcelize
    data class CommentEntity(
        val id: Long,
        val description: String,
        val commentsUrl: String,
        val updatedAt: String,
        val user: IssueCommonModels.UserEntity
    ) : Parcelable

    @Keep
    data class CommentResponse(
        @SerializedName("id")
        val id: Long,

        @SerializedName("body")
        val body: String,

        @SerializedName("url")
        val url: String,

        @SerializedName("created_at")
        val createdAt: Date,

        @SerializedName("updated_at")
        val updatedAt: Date,

        @SerializedName("user")
        val user: IssueCommonModels.UserResponse
    )
}