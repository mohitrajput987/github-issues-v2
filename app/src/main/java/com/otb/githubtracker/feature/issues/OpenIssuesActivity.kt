package com.otb.githubtracker.feature.issues

import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.otb.githubtracker.common.LoadingSpinner
import com.otb.githubtracker.common.base.BaseActivity
import com.otb.githubtracker.common.base.DisplaysLoadingSpinner
import com.otb.githubtracker.databinding.ActivityOpenIssuesBinding
import com.otb.githubtracker.network.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenIssuesActivity : BaseActivity<ActivityOpenIssuesBinding>(), DisplaysLoadingSpinner {
    companion object {
        private const val DEFAULT_ORG_NAME = "square"
        private const val DEFAULT_REPO_NAME = "okhttp"
    }

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityOpenIssuesBinding.inflate(layoutInflater)

    private val viewModel by viewModels<OpenIssuesViewModel>()
    private val openIssuesAdapter by lazy { OpenIssuesAdapter() }
    override val loadingSpinner by lazy { LoadingSpinner(this) }

    override fun setupView() {
        setupRecyclerView()
        observeIssues()
        viewModel.fetchIssues(DEFAULT_ORG_NAME, DEFAULT_REPO_NAME)
    }

    private fun setupRecyclerView() {
        val verticalLayoutManager = LinearLayoutManager(this@OpenIssuesActivity)
        binding.rvIssues.apply {
            layoutManager = verticalLayoutManager
            adapter = openIssuesAdapter
            addItemDecoration(DividerItemDecoration(baseContext, verticalLayoutManager.orientation))
        }
    }

    private fun observeIssues() {
        viewModel.openIssuesLiveData.observe(this) {
            when (it) {
                is ViewState.Loading -> showLoadingSpinner()
                is ViewState.Success -> showIssues(it.data)
                is ViewState.Error -> showErrorMessage(it.errorMessage)
            }
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        dismissLoadingSpinner()
        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showIssues(issueEntities: List<OpenIssuesModels.IssueEntity>) {
        dismissLoadingSpinner()
        openIssuesAdapter.submitList(issueEntities)
    }
}