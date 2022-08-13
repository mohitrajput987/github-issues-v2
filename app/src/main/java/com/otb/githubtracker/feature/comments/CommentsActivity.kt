package com.otb.githubtracker.feature.comments

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.otb.githubtracker.common.LoadingSpinner
import com.otb.githubtracker.common.base.BaseActivity
import com.otb.githubtracker.common.base.DisplaysLoadingSpinner
import com.otb.githubtracker.databinding.ActivityCommentsBinding
import com.otb.githubtracker.feature.issues.OpenIssuesModels
import com.otb.githubtracker.network.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsActivity : BaseActivity<ActivityCommentsBinding>(), DisplaysLoadingSpinner {
    companion object {
        const val ISSUE_ENTITY = "issue_entity"

        fun getIntent(context: Context, issueEntity: OpenIssuesModels.IssueEntity) =
            Intent(context, CommentsActivity::class.java).apply {
                putExtra(ISSUE_ENTITY, issueEntity)
            }
    }

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityCommentsBinding.inflate(layoutInflater)

    private val viewModel by viewModels<CommentsViewModel>()
    private val commentAdapter by lazy { CommentAdapter() }
    override val loadingSpinner by lazy { LoadingSpinner(this) }

    override fun setupView() {
        setupRecyclerView()
        observeIssueDetails()
        observeComments()
        viewModel.fetchComments()
    }

    private fun setupRecyclerView() {
        val verticalLayoutManager = LinearLayoutManager(this@CommentsActivity)
        binding.rvComments.apply {
            layoutManager = verticalLayoutManager
            adapter = commentAdapter
            addItemDecoration(DividerItemDecoration(baseContext, verticalLayoutManager.orientation))
        }
    }

    private fun observeComments() {
        viewModel.commentsLiveData.observe(this) {
            when (it) {
                is ViewState.Loading -> showLoadingSpinner()
                is ViewState.Success -> showComments(it.data)
                is ViewState.Error -> showErrorMessage(it.errorMessage)
            }
        }
    }

    private fun observeIssueDetails() {
        viewModel.issueLiveData.observe(this) {
            binding.issueEntity = it
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        dismissLoadingSpinner()
        binding.rvComments.isVisible = true
        binding.tvError.isVisible = false
        binding.tvError.text = errorMessage
    }

    private fun showComments(commentEntities: List<CommentsModels.CommentEntity>) {
        dismissLoadingSpinner()
        if (commentEntities.isEmpty()) {
            binding.rvComments.isVisible = false
            binding.tvError.isVisible = true
        } else {
            binding.rvComments.isVisible = true
            binding.tvError.isVisible = false
            commentAdapter.submitList(commentEntities)
        }
    }
}