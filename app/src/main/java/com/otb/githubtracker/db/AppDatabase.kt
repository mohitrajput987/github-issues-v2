package com.otb.githubtracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.otb.githubtracker.feature.IssueCommonModels
import com.otb.githubtracker.feature.comments.CommentsModels
import com.otb.githubtracker.feature.issues.OpenIssuesModels
import java.util.*

/**
 * Created by Mohit Rajput on 13/08/22.
 */

@Database(
    entities = [IssueCommonModels.UserResponse::class, OpenIssuesModels.IssueResponse::class, CommentsModels.CommentResponse::class],
    version = 1
)
@TypeConverters(AppDatabase.DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun issuesDao(): IssuesDao
    abstract fun commentsDao(): CommentsDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "github-issues.db"
                    ).build()
                }
            }
            return instance!!
        }
    }

    object DateConverter {
        @TypeConverter
        fun toDate(dateLong: Long?): Date? {
            return dateLong?.let { Date(it) }
        }

        @TypeConverter
        fun fromDate(date: Date?): Long? {
            return date?.time
        }
    }
}