package com.example.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.legalqa.CommentReplies
import com.example.legalqa.Comments
import com.example.legalqa.databinding.CommentsItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class CommentsAdapter(private val mCommentsList: List<Comments>, private val mCommentsReplies: List<CommentReplies>, private val commentClickListener: CommentClickListener) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    private val employeeEventChannel = Channel<CommentEvent>()
    val employeesEvent = employeeEventChannel.receiveAsFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = CommentsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val currentItem = mCommentsList[position]
        holder.bind(currentItem, mCommentsReplies, commentClickListener, employeeEventChannel)
    }

    override fun getItemCount(): Int {
        Log.e(TAG, "getItemCount: $mCommentsList.size")
        return mCommentsList.size
    }
    class CommentsViewHolder(private val binding: CommentsItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(
            currentItem: Comments,
            mCommentsReplies: List<CommentReplies>,
            commentClickListener: CommentClickListener,
            employeeEventChannel: Channel<CommentEvent>
        ) {
            val commentsRepliesAdapter = CommentsRepliesAdapter(mCommentsReplies,

                ReplyActionListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        //directionFragment.show((supportFragmentManager, directionFragment.tag)
                        employeeEventChannel.send(CommentEvent.OpenActionForReplyingUser)
                    }
                }
            )
            binding.apply { //= currentItem
                listener = commentClickListener
                comments = currentItem
                Log.e(TAG, "bind: $currentItem")
                recviewNestedCommentsReplies.apply {
                    adapter = commentsRepliesAdapter
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                }
            }
        }
    }

    sealed class CommentEvent() {
        object OpenActionForReplyingUser : CommentEvent()
    }
}

class CommentClickListener(val clickListener: () -> Unit){
    fun onThreeDotsClick() = clickListener()
}

