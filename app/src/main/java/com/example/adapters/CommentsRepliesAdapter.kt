package com.example.adapters

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.legalqa.CommentReplies
import com.example.legalqa.databinding.CommentsRepliesItemsBinding

class CommentsRepliesAdapter(private val mCommentReplies: List<CommentReplies>, private val actionReplyListener: ReplyActionListener): RecyclerView.Adapter<CommentsRepliesAdapter.CommentRepliesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRepliesViewHolder {
        val binding = CommentsRepliesItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return CommentRepliesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentRepliesViewHolder, position: Int) {
        val currentItem = mCommentReplies[position]
        holder.bind(currentItem, actionReplyListener)
    }

    override fun getItemCount(): Int {
        return mCommentReplies.size
    }

    class CommentRepliesViewHolder(private val binding: CommentsRepliesItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: CommentReplies, actionReplyListener: ReplyActionListener) {
            binding.apply {
                listener = actionReplyListener
                //= currentItem
                Log.e(TAG, "bind_commentsReplies: $currentItem ", )
                comments = currentItem
            }
        }
    }

}

class ReplyActionListener(val clickListener: () -> Unit){
    fun onReplyThreeDotsClick() = clickListener()
}