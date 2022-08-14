package com.otb.githubtracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otb.githubtracker.feature.issues.OpenIssuesModels

@Dao
interface IssuesDao {
    @Query("SELECT * FROM issue ORDER BY createdAt DESC")
    suspend fun getAllIssues(): List<OpenIssuesModels.IssueResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(issues: List<OpenIssuesModels.IssueResponse>)

    @Query("DELETE FROM issue")
    suspend fun clear()
}