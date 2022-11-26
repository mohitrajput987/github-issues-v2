package com.otb.githubtracker.feature.issues.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.otb.githubtracker.R
import com.otb.githubtracker.common.ui.CircleImage
import com.otb.githubtracker.common.ui.ErrorScreen
import com.otb.githubtracker.common.ui.Header
import com.otb.githubtracker.feature.issues.OpenIssuesActivity
import com.otb.githubtracker.feature.issues.OpenIssuesModels
import com.otb.githubtracker.feature.issues.OpenIssuesViewModel
import com.otb.githubtracker.network.ViewState
import com.otb.githubtracker.theme.Teal200
import com.otb.githubtracker.theme.Typography
import com.otb.githubtracker.theme.White
import com.otb.githubtracker.theme.cardElevation
import com.otb.githubtracker.theme.horizontalMargin
import com.otb.githubtracker.theme.itemGap

/**
 * Created by Mohit Rajput on 19/11/22.
 */

@Composable
fun OpenIssuesScreen(activity: OpenIssuesActivity, viewModel: OpenIssuesViewModel) {
    LaunchedEffect(true) {
        viewModel.fetchIssues(
            OpenIssuesActivity.DEFAULT_ORG_NAME,
            OpenIssuesActivity.DEFAULT_REPO_NAME
        )
    }
    val issuesResult by viewModel.openIssuesLiveData.observeAsState()
    if (issuesResult is ViewState.Loading) {
        activity.loadingSpinner.show()
    } else {
        activity.loadingSpinner.dismiss()
    }

    val onItemClick = { issueEntity: OpenIssuesModels.IssueEntity ->
        activity.onIssueItemClick(issueEntity)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Header(title = stringResource(id = R.string.app_name))
        Spacer(modifier = Modifier.height(itemGap))
        when (issuesResult) {
            is ViewState.Error -> {
                ErrorScreen((issuesResult as ViewState.Error).errorMessage)
            }
            is ViewState.Success -> {
                val issues = (issuesResult as ViewState.Success).data
                LazyColumn(modifier = Modifier.padding(horizontal = horizontalMargin)) {
                    items(issues) { issueEntity ->
                        IssueItem(issueEntity = issueEntity, onItemClick)
                        Spacer(modifier = Modifier.height(itemGap))
                    }
                }
            }
            else -> Unit
        }
    }
}


@Composable
fun IssueItem(
    issueEntity: OpenIssuesModels.IssueEntity,
    onClick: (OpenIssuesModels.IssueEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White)
            .clickable { onClick(issueEntity) },
        elevation = cardElevation,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = issueEntity.title,
                style = Typography.h3
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = issueEntity.description,
                style = Typography.body1
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.opened_by),
                    style = Typography.body1,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(5.dp))
                CircleImage(
                    imageUrl = issueEntity.user.imageUrl,
                    placeholderResId = R.drawable.ic_user_default
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = issueEntity.user.userName,
                    style = Typography.body1,
                    color = Teal200
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.updated_at_x, issueEntity.updatedAt),
                style = Typography.caption
            )
        }
    }
}