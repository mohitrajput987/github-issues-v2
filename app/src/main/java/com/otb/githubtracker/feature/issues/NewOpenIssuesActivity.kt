package com.otb.githubtracker.feature.issues

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.otb.githubtracker.common.LoadingSpinner
import com.otb.githubtracker.common.base.DisplaysLoadingSpinner
import com.otb.githubtracker.feature.comments.CommentsActivity
import com.otb.githubtracker.feature.issues.ui.OpenIssuesScreen
import com.otb.githubtracker.theme.GitHubIssuesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewOpenIssuesActivity : ComponentActivity(), DisplaysLoadingSpinner {
    companion object {
        const val DEFAULT_ORG_NAME = "square"
        const val DEFAULT_REPO_NAME = "okhttp"
    }

    private val viewModel by viewModels<OpenIssuesViewModel>()
    override val loadingSpinner by lazy { LoadingSpinner(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubIssuesTheme {
                OpenIssuesScreen(activity = this, viewModel = viewModel)
            }
        }
    }

    fun onIssueItemClick(issueEntity: OpenIssuesModels.IssueEntity) {
        startActivity(CommentsActivity.getIntent(this, issueEntity))
    }
}