package com.otb.githubtracker.feature.issues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.otb.githubtracker.databinding.ItemIssueBinding

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class OpenIssuesAdapter(private val onItemClick: (OpenIssuesModels.IssueEntity) -> Unit) :
    ListAdapter<OpenIssuesModels.IssueEntity, OpenIssuesAdapter.ViewHolder>(DIFF_UTIL) {
    companion object {
        private val DIFF_UTIL =
            object : DiffUtil.ItemCallback<OpenIssuesModels.IssueEntity>() {
                override fun areItemsTheSame(
                    oldItem: OpenIssuesModels.IssueEntity,
                    newItem: OpenIssuesModels.IssueEntity
                ) = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: OpenIssuesModels.IssueEntity,
                    newItem: OpenIssuesModels.IssueEntity
                ): Boolean {
                    return oldItem.title == newItem.title && oldItem.description ==
                            newItem.description && oldItem.updatedAt == newItem.updatedAt && oldItem.user.id == newItem.user.id
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemIssueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemIssueBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                binding.issueEntity?.let {
                    onItemClick(it)
                }
            }
        }

        fun bind(issueEntity: OpenIssuesModels.IssueEntity) {
            binding.issueEntity = issueEntity
            binding.executePendingBindings()
        }
    }
}