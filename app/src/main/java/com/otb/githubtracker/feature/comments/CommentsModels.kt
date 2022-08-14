package com.otb.githubtracker.feature.comments

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
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
        val issueUrl: String,
        val updatedAt: String,
        val user: IssueCommonModels.UserEntity
    ) : Parcelable

    @Keep
    @Entity(tableName = "comment")
    data class CommentResponse(
        @SerializedName("id")
        @PrimaryKey
        val id: Long,

        @SerializedName("body")
        val body: String,

        @SerializedName("issue_url")
        val issueUrl: String,

        @SerializedName("created_at")
        val createdAt: Date,

        @SerializedName("updated_at")
        val updatedAt: Date,

        @SerializedName("user")
        @Embedded
        val user: IssueCommonModels.UserResponse
    )
}