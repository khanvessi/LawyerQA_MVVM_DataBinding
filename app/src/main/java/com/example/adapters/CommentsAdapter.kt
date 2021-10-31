package com.example.adapters

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
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


class CommentsAdapter(private val mCommentsList: List<Comments>, private val mCommentsReplies: List<CommentReplies>,
                      private val commentClickListener: CommentClickListener,
                      private val isShowClicked: MutableLiveData<Boolean>,
                      private val lifecycleOwner: LifecycleOwner,
                      private val clickedPosition: MutableLiveData<Int>
                      ) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    var rowId = 0

    private val commentEvent = Channel<CommentEvent>()
    val commentEventChannelFromAdapter = commentEvent.receiveAsFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = CommentsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        binding.lifecycleOwner = this@CommentsAdapter.lifecycleOwner
        return CommentsViewHolder(binding, isShowClicked)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val currentItem = mCommentsList[position]
        holder.bind(currentItem, mCommentsReplies,mCommentsList, commentClickListener, commentEvent, clickedPosition, rowId)
//
        holder.itemView.setOnClickListener(View.OnClickListener {
            rowId = position
            holder.bind(currentItem, mCommentsReplies, mCommentsList, commentClickListener, commentEvent, clickedPosition, rowId)
            notifyDataSetChanged()
        })
    }

    override fun getItemCount(): Int {
        Log.e(TAG, "getItemCount: $mCommentsList.size")
        return mCommentsList.size
    }

    class CommentsViewHolder(
        private val binding: CommentsItemBinding,
        private val isShowClicked: MutableLiveData<Boolean>
    ) : RecyclerView.ViewHolder(binding.root){

        var rowId = 10

        @SuppressLint("SetTextI18n")
        fun bind(
            currentItem: Comments,
            mCommentsReplies: List<CommentReplies>,
            mComments: List<Comments>,
            commentClickListener: CommentClickListener,
            employeeEventChannel: Channel<CommentEvent>,
            clickedPosition: MutableLiveData<Int>,
            rowId: Int
        ) {
            val commentsRepliesAdapter = CommentsRepliesAdapter(mCommentsReplies,
                ReplyActionListener ( {
                    CoroutineScope(Dispatchers.IO).launch {
                        //directionFragment.show((supportFragmentManager, directionFragment.tag)
                        employeeEventChannel.send(CommentEvent.OpenActionForReplyingUser)
                    }
                }, {
                    //TODO HANDLE SUB REPLY HERE
                }), mComments)
            binding.apply { //= currentItem
                listener = commentClickListener
                comments = currentItem
                Log.e(TAG, "bind: $currentItem")
                recviewNestedCommentsReplies.apply {
                    adapter = commentsRepliesAdapter
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                }

            }

            isShowClicked.observe(requireNotNull(binding.lifecycleOwner)){
                Log.e(TAG, "BOUND: $rowId " , )
                if(clickedPosition.value == rowId){
                    if(it == true){
                        binding.txtShowHideReplies.text = "Hide Replies"
                        binding.recviewNestedCommentsReplies.visibility = View.VISIBLE
                    }
                    else{
                        binding.txtShowHideReplies.text = "Show Replies"
                        binding.recviewNestedCommentsReplies.visibility = View.GONE
                    }
                }

            }



            binding.executePendingBindings()
        }

        fun makeChanges() {
            binding.txtShowHideReplies.text = "Hide Replies"
            binding.recviewNestedCommentsReplies.visibility = View.VISIBLE
        }
    }

    sealed class CommentEvent() {
        object OpenActionForReplyingUser : CommentEvent()
    }
}

class CommentClickListener(val clickListener: () -> Unit, val onReplyClick: () -> Unit, val onHideShow:(pos: Int) -> Unit){
    fun onThreeDotsClick() = clickListener()
    fun onReplyTextClick() = onReplyClick()
    fun onHideShowReplies(pos: Int) = onHideShow(pos)
}

