package com.otb.githubtracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otb.githubtracker.feature.comments.CommentsModels

@Dao
interface CommentsDao {
    @Query("SELECT * FROM comment WHERE issueUrl = :issueUrl")
    suspend fun getCommentOfIssue(issueUrl: String): List<CommentsModels.CommentResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<CommentsModels.CommentResponse>)

    @Query("DELETE FROM comment WHERE issueUrl = :issueUrl")
    suspend fun deleteCommentsOfIssue(issueUrl: String)
}