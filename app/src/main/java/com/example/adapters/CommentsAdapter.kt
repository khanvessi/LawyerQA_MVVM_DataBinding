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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.*


class CommentsAdapter(
    private val mCommentsList: List<Comments>, private val mCommentsReplies: List<CommentReplies>,
    private val commentClickListener: CommentClickListener,
    private val isShowClicked: MutableLiveData<Boolean>,
    private val lifecycleOwner: LifecycleOwner,
    private val clickedPosition: MutableLiveData<Int>,
    private val observerForSubrepliesThreeDots: MutableLiveData<Boolean>,
    private val observerForSubreplies: MutableLiveData<String>
) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    var userComment = MutableLiveData<Boolean>()
    private val commentEvent = Channel<CommentEvent>()
    val commentEventChannelFromAdapter = commentEvent.receiveAsFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = CommentsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        binding.lifecycleOwner = this@CommentsAdapter.lifecycleOwner
        Log.e(TAG, "onCreateViewHolder: CALLED")
        return CommentsViewHolder(binding, isShowClicked, mCommentsList, mCommentsReplies)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val currentItem = mCommentsList[position]
        holder.bind(
            currentItem,
            mCommentsList,
            commentClickListener,
            commentEvent,
            clickedPosition,
            position,
            observerForSubrepliesThreeDots,
            observerForSubreplies,
            userComment
        )
        Log.e(TAG, "onBindViewHolder: CALLLLLLLLLLLLLLLLLLLLLLLLLLLLLED $position")

    }

    override fun getItemCount(): Int {
        Log.e(TAG, "getItemCount: $mCommentsList.size")
        if(userComment.value != true){
            Collections.reverse(mCommentsList)
            userComment.value = false
        }
        return mCommentsList.size
    }

    class CommentsViewHolder(
        private val binding: CommentsItemBinding,
        private val isShowClicked: MutableLiveData<Boolean>,
        private val mComments: List<Comments>,
        private val mCommentsReplies: List<CommentReplies>
    ) : RecyclerView.ViewHolder(binding.root) {


        var _repliesList: MutableList<CommentReplies> = arrayListOf()

        var rowId = 10

        @SuppressLint("SetTextI18n")
        fun bind(
            currentItem: Comments,
            mComments: List<Comments>,
            commentClickListener: CommentClickListener,
            employeeEventChannel: Channel<CommentEvent>,
            clickedPosition: MutableLiveData<Int>,
            pos: Int,
            observerForSubrepliesThreeDots: MutableLiveData<Boolean>,
            observerForSubreplies: MutableLiveData<String>,
            ifUserNotComment: MutableLiveData<Boolean>
        ) {
            val commentsRepliesAdapter = CommentsRepliesAdapter(_repliesList,
                ReplyActionListener({
                    observerForSubrepliesThreeDots.value = true
//                    CoroutineScope(Dispatchers.IO).launch {
//                        employeeEventChannel.send(CommentEvent.OpenActionForReplyingUser)
//                    }
                }, {
                    observerForSubreplies.value = it.commentBy
                    ifUserNotComment.value = true
                })
            )
            binding.apply { //= currentItem
                listener = commentClickListener
                comments = currentItem
                //position = pos
                addReplies(currentItem)
                Log.e(TAG, "bind: $currentItem")
                recviewNestedCommentsReplies.apply {
                    adapter = commentsRepliesAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                }

            }


            isShowClicked.observe(requireNotNull(binding.lifecycleOwner)) {
                Log.e(TAG, "BOUND: $rowId ")
                Log.e(TAG, "CLICKED POSTIONS: " + clickedPosition.value)


                if (clickedPosition.value == adapterPosition) {
                    if (binding.txtShowHideReplies.text == "Hide Replies") {
                        binding.txtShowHideReplies.text = "Show Replies"
                    } else {
                        binding.txtShowHideReplies.text = "Hide Replies"
                    }

                    if (binding.recviewNestedCommentsReplies.visibility == View.VISIBLE) {
                        binding.recviewNestedCommentsReplies.visibility = View.GONE
                    } else {
                        binding.recviewNestedCommentsReplies.visibility = View.VISIBLE
                    }
                }

            }

        }


        private fun addReplies(commentsReplies: Comments) {
            Log.e(TAG, "SIZE OF REPLIES: " + mCommentsReplies.size)
            for (replies in mCommentsReplies) {
                if (commentsReplies.commentId == replies.parentCommentId) {
                    Log.e(TAG, "addReplies: LIST OF REPLIES $replies")
                    _repliesList.add(replies)
                }
            }
        }


//        private fun addReplies(commentsReplies: Comments) {
//            val _commentList = MutableLiveData(mComments)
//            val newList = _commentList.value ?: mutableListOf()
//            newList.find { it.commentId == commentsReplies.parentCommentId }?.commentReplies?.add(commentsReplies)
//            _repliesList = newList.firstOrNull()?.commentReplies!!
//            Log.e(TAG, "addReplies: $_repliesList", )
//
////    else {
////            //TODO: ADD NEW COMMENTS IF NEEDED
////            //newList.add(shortComment)
////        }
////       // _commentList.value = newList
//


    }

    sealed class CommentEvent() {
        object OpenActionForReplyingUser : CommentEvent()
    }
}

class CommentClickListener(
    val clickListener: () -> Unit,
    val onReplyClick: (comment: Comments) -> Unit,
    val onHideShow: (pos: Int) -> Unit
) {
    fun onThreeDotsClick() = clickListener()
    fun onReplyTextClick(comment: Comments) = onReplyClick(comment)
    fun onHideShowReplies(pos: Int) = onHideShow(pos)
}

