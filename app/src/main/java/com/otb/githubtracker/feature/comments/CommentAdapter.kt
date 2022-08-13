package com.otb.githubtracker.feature.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.otb.githubtracker.databinding.ItemCommentBinding

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class CommentAdapter :
    ListAdapter<CommentsModels.CommentEntity, CommentAdapter.ViewHolder>(DIFF_UTIL) {
    companion object {
        private val DIFF_UTIL =
            object : DiffUtil.ItemCallback<CommentsModels.CommentEntity>() {
                override fun areItemsTheSame(
                    oldItem: CommentsModels.CommentEntity,
                    newItem: CommentsModels.CommentEntity
                ) = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: CommentsModels.CommentEntity,
                    newItem: CommentsModels.CommentEntity
                ): Boolean {
                    return oldItem.description == newItem.description && oldItem.updatedAt == newItem.updatedAt && oldItem.user.id == newItem.user.id
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(commentEntity: CommentsModels.CommentEntity) {
            binding.commentEntity = commentEntity
            binding.executePendingBindings()
        }
    }
}