package com.otb.githubtracker.feature.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otb.githubtracker.network.ApiResult
import com.otb.githubtracker.network.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Mohit Rajput on 13/08/22.
 */

@HiltViewModel
class OpenIssuesViewModel @Inject constructor(private val repository: OpenIssuesContact.Repository) :
    ViewModel() {
    private val _openIssuesLiveData =
        MutableLiveData<ViewState<List<OpenIssuesModels.IssueEntity>>>()
    val openIssuesLiveData: LiveData<ViewState<List<OpenIssuesModels.IssueEntity>>> get() = _openIssuesLiveData

    private val openIssuesMapper = OpenIssuesMapper()
    private var pageNum = 1

    fun fetchIssues(
        organizationName: String,
        repositoryName: String
    ) {
        _openIssuesLiveData.value = ViewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result =
                repository.fetchIssues(
                    organizationName,
                    repositoryName,
                    pageNum
                )) {
                is ApiResult.Success -> {
                    withContext(Dispatchers.Main) {
                        val issueEntities = openIssuesMapper.mapFrom(result.data)
                        _openIssuesLiveData.value = ViewState.Success(issueEntities)
                    }
                }
                is ApiResult.Error -> {
                    withContext(Dispatchers.Main) {
                        _openIssuesLiveData.value = ViewState.Error(result.message)
                    }
                }
            }
        }
    }
}