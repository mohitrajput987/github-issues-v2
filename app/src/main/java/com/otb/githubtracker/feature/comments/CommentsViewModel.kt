package com.otb.githubtracker.feature.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otb.githubtracker.feature.issues.OpenIssuesModels
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
class CommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CommentsContact.Repository
) :
    ViewModel() {
    private val _commentsLiveData =
        MutableLiveData<ViewState<List<CommentsModels.CommentEntity>>>()
    val commentsLiveData: LiveData<ViewState<List<CommentsModels.CommentEntity>>> get() = _commentsLiveData

    private val issue by lazy { savedStateHandle.get<OpenIssuesModels.IssueEntity>(CommentsActivity.ISSUE_ENTITY)!! }

    val issueLiveData : LiveData<OpenIssuesModels.IssueEntity> = MutableLiveData(issue)

    private val commentsMapper = CommentsMapper()

    fun fetchComments() {
        _commentsLiveData.value = ViewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result =
                repository.fetchComments(issue.commentsUrl)) {
                is ApiResult.Success -> {
                    withContext(Dispatchers.Main) {
                        val issueEntities = commentsMapper.mapFrom(result.data)
                        _commentsLiveData.value = ViewState.Success(issueEntities)
                    }
                }
                is ApiResult.Error -> {
                    withContext(Dispatchers.Main) {
                        _commentsLiveData.value = ViewState.Error(result.message)
                    }
                }
            }
        }
    }
}