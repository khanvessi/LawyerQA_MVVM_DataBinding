package com.example.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.legalqa.CommentReplies
import com.example.legalqa.Comments
import com.example.legalqa.databinding.CommentsRepliesItemsBinding
import java.util.*

class CommentsRepliesAdapter(private val mCommentReplies: List<CommentReplies>, private val actionReplyListener: ReplyActionListener): RecyclerView.Adapter<CommentsRepliesAdapter.CommentRepliesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRepliesViewHolder {
        val binding = CommentsRepliesItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return CommentRepliesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentRepliesViewHolder, position: Int) {
        val currentItem = mCommentReplies[position]
        //Log.e(TAG, "onBindViewHolder: CALLED", )
        holder.bind(currentItem, actionReplyListener)
    }

    override fun getItemCount(): Int {
        val repliesList = mCommentReplies.distinctBy { it.comment  }
        Collections.reverse(repliesList)
        return repliesList.size
    }

    class CommentRepliesViewHolder(private val binding: CommentsRepliesItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            currentItem: CommentReplies,
            actionReplyListener: ReplyActionListener
        ) {
            binding.apply {
                listener = actionReplyListener
                commentReplies = currentItem
            }
        }


    }
}

class ReplyActionListener(val clickListener: () -> Unit, val onSubReplyClic: (commentsReplies: CommentReplies) -> Unit){
    fun onReplyThreeDotsClick() = clickListener()
    fun onSubReplyClick(commentReplies: CommentReplies) = onSubReplyClic(commentReplies)
}