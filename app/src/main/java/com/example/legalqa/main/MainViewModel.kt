package com.example.legalqa.main

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import android.content.res.AssetManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.legalqa.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {
    var  observerForSubReplies = MutableLiveData<String>()
    var  observerForSubReplies1 = MutableLiveData<Boolean>()
    var observerForSubRepliesThreedots = MutableLiveData<Boolean>()
    var isReplying = MutableLiveData<Boolean>()
    var observeIsplaying = MutableLiveData<Comments>()
    var clickedPosition = MutableLiveData<Int>()
    private val mainEvent = Channel<MainActivityEvent>()
    val mainActivityChannel = mainEvent.receiveAsFlow()
    val mLawyerQueryList = MutableLiveData<LawyerQuery>()
    var questionDetail = MutableLiveData<QuestionDetail>()
    var listOfCommentsAttachments = MutableLiveData<List<CommentAttachment>>()
    val edtCommentBox = MutableLiveData<String>()
    val isShowClicked = MutableLiveData<Boolean>()
    var comments: List<Comments>? = null
    var commentAttachment: List<CommentAttachment>? = null
    var commentsReplies: List<CommentReplies>? = null
    var qDetails: QuestionDetail? = null
    var attachmentList: List<QuestionAttachments>? = null


    //TODO: HANDLE UPLOAD BTN CLICK
    fun onUploadBtnClick(){
        viewModelScope.launch {
            Log.e(TAG, "onUploadBtnClick: CALLED", )
            mainEvent.send(MainActivityEvent.OnUploadBtnClicked)
        }
    }

    fun onCancelReplyingTo(){
        isReplying.value = false
        viewModelScope.launch {
            Log.e(TAG, "onUploadBtnClick: CALLED", )
            mainEvent.send(MainActivityEvent.OnCancelReplyingTo)
        }
    }

    fun onSendComment(){

        val com = observeIsplaying.value
        if(isReplying.value == true){
            //TODO: COMMENT REPLY ON SEND COMMENT
            Log.e(TAG, "onSendComment: $com", )
            if(!edtCommentBox.value.isNullOrEmpty()) {
                var mReplies = commentsReplies?.toMutableList()
                if (com != null) {
                    mReplies?.add(
                        CommentReplies(
                            10,
                            com.commentId,
                            1,
                            edtCommentBox.value.toString(),
                            "Shan",
                            1,
                            "Just now",
                            "",
                            2,
                            com.commentBy
                        )
                    )

                    commentsReplies = mReplies

                    viewModelScope.launch {
                        qDetails?.let {
                            comments?.let { it1 ->
                                MainActivityEvent.AddingCommentsAndRepliesToAdapter(
                                    it1,commentsReplies,
                                    it
                                )
                            }
                        }?.let { mainEvent.send(it) }
                    }
                }

            }else{
                viewModelScope.launch {
                    mainEvent.send(MainActivityEvent.ShowValidationMessage)
                }
            }
            }else if(observerForSubReplies1.value == true){
            //TODO: HANDLE SUB REPLY NOW
            if(!edtCommentBox.value.isNullOrEmpty()) {


            }else{
                viewModelScope.launch {
                    mainEvent.send(MainActivityEvent.ShowValidationMessage)
                }
            }
        }
        else{
            if(!edtCommentBox.value.isNullOrEmpty()){
                var mComments = comments?.toMutableList()
                commentAttachment?.let {
                    edtCommentBox.value?.let { it1 ->
                        Comments(0,4,1, it1,
                            "Somebody",
                            3,
                            "Just now",
                            "",
                            1,
                            "",
                            it,
                            commentsReplies as MutableList<CommentReplies>
                        )
                    }
                }?.let {
                    mComments?.add(
                        it
                    )
                }
                comments = mComments

                viewModelScope.launch {
                    qDetails?.let {
                        MainActivityEvent.AddingCommentsAndRepliesToAdapter(comments!!,commentsReplies,
                            it
                        )
                    }?.let { mainEvent.send(it) }
                    attachmentList?.let {
                        MainActivityEvent.AddingAttachmentToAdapter(
                            it
                        )
                    }?.let { mainEvent.send(it) }
                }

                viewModelScope.launch {
                    mainEvent.send(MainActivityEvent.ShowSuccessComment)
                }

            }else{
                viewModelScope.launch {
                    mainEvent.send(MainActivityEvent.ShowValidationMessage)
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun getDataFromJson(context: Context) {
        val json = loadJSONFromAsset(context)
        Log.e(ContentValues.TAG, "onCreate: $json")
        val parser = JsonParser()
        val mJson: JsonElement = parser.parse(json)
        val gson = Gson()
        //mainViewModel.mLawyerQueryList.value = gson.fromJson(mJson, LawyerQuery::class.java)
        val lawyerQuery = gson.fromJson(mJson, LawyerQuery::class.java)
        Log.e(ContentValues.TAG, "onCreate: LAWYER OBJECT:${mLawyerQueryList.value}")

        commentAttachment = lawyerQuery.comment.comments.flatMap { it.commentAttachment }

        comments = lawyerQuery.comment.comments
        val mListofReplies = mutableListOf<CommentReplies>()

        val result = lawyerQuery.comment.comments.flatMap { it.commentReplies }
        Log.e(TAG, "getDataFromJson: RESULT: $result", )
        Log.e(TAG, "getDataFromJson: RESULT: "+result.size, )
        commentsReplies = result

        qDetails = lawyerQuery.qDetails
        attachmentList = lawyerQuery.qDetails.questionAttachmentsList

        viewModelScope.launch {
            mainEvent.send(MainActivityEvent.AddingCommentsAndRepliesToAdapter(comments!!,commentsReplies,
                qDetails!!
            ))
            mainEvent.send(MainActivityEvent.AddingAttachmentToAdapter(attachmentList!!))
        }

    }


    private fun loadJSONFromAsset(context: Context): String? {
        val am: AssetManager = context.assets
        val charset: Charset = Charsets.UTF_8
        var json: String? = null
        json = try {
            val `is`: InputStream = am.open("lawyer_qn.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    sealed class MainActivityEvent{
        data class AddingCommentsAndRepliesToAdapter(val comments: List<Comments>, val commentsReplies: List<CommentReplies>?, val questionDetail: QuestionDetail) : MainActivityEvent()
        data class AddingAttachmentToAdapter(val attachmentList: List<QuestionAttachments>) : MainActivityEvent()
        object OnUploadBtnClicked: MainActivityEvent()
        object ShowValidationMessage : MainActivityEvent()
        object ShowSuccessComment : MainActivityEvent()
        object OnCancelReplyingTo : MainActivityEvent()
    }
}