package com.otb.githubtracker.feature

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class IssueCommonModels {
    @Parcelize
    data class UserEntity(
        val id: Long,
        val userName: String,
        val imageUrl: String
    ) : Parcelable


    @Keep
    @Entity(tableName = "user")
    data class UserResponse(
        @SerializedName("id")
        @ColumnInfo(name = "user_id")
        @PrimaryKey
        val id: Long,

        @SerializedName("login")
        val userName: String,

        @SerializedName("avatar_url")
        val avatarUrl: String,

        @SerializedName("type")
        val type: String,
    )
}