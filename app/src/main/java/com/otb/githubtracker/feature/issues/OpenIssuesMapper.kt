package com.otb.githubtracker.feature.issues

import com.otb.githubtracker.common.base.Mapper
import com.otb.githubtracker.util.DateUtils

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class OpenIssuesMapper :
    Mapper<List<OpenIssuesModels.IssueResponse>, List<OpenIssuesModels.IssueEntity>> {
    override fun mapFrom(from: List<OpenIssuesModels.IssueResponse>): List<OpenIssuesModels.IssueEntity> {
        return from.map {
            val user = OpenIssuesModels.UserEntity(it.user.id, it.user.userName, it.user.avatarUrl)
            OpenIssuesModels.IssueEntity(
                id = it.id,
                title = it.title,
                description = getTrimmedBody(it.body),
                updatedAt = DateUtils.getFormattedTime(it.createdAt),
                user = user
            )
        }
    }

    private fun getTrimmedBody(text: String?): String {
        val descriptionMaxLength = 200
        return if (text == null) return ""
        else if (text.length > descriptionMaxLength) text.replaceRange(
            descriptionMaxLength,
            text.length,
            "..."
        ) else text
    }
}
