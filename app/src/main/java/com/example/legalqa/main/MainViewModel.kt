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
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.adapters.QuestionAttachmentAdapter
import com.example.legalqa.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {
    var clickedPosition = MutableLiveData<Int>()
    private val mainEvent = Channel<MainActivityEvent>()
    val mainActivityChannel = mainEvent.receiveAsFlow()
    val mLawyerQueryList = MutableLiveData<LawyerQuery>()
    var questionDetail = MutableLiveData<QuestionDetail>()
    var listOfCommentsAttachments = MutableLiveData<List<CommentAttachment>>()
    val edtCommentBox = MutableLiveData<String>()
    val isShowClicked = MutableLiveData<Boolean>()


    //TODO: HANDLE UPLOAD BTN CLICK
    fun onUploadBtnClick(){
        viewModelScope.launch {
            Log.e(TAG, "onUploadBtnClick: CALLED", )
            mainEvent.send(MainActivityEvent.OnUploadBtnClicked)
        }
    }

    fun test(){
        Log.e(TAG, "test: TEST", )
    }

    fun test1(){
        Log.e(TAG, "test: TEST", )
    }


    fun getDataFromJson(context: Context) {
        val json = loadJSONFromAsset(context)
        Log.e(ContentValues.TAG, "onCreate: $json")
        val parser = JsonParser()
        val mJson: JsonElement = parser.parse(json)
        val gson = Gson()
        //mainViewModel.mLawyerQueryList.value = gson.fromJson(mJson, LawyerQuery::class.java)
        val lawyerQuery = gson.fromJson(mJson, LawyerQuery::class.java)
        Log.e(ContentValues.TAG, "onCreate: LAWYER OBJECT:${mLawyerQueryList.value}")

        val comments = lawyerQuery.comment.comments
        val commentsReplies = lawyerQuery.comment.comments.firstOrNull()?.commentReplies

        if (commentsReplies != null) {
            for (rel in commentsReplies){

            }
        }
        val questionDetail = lawyerQuery.qDetails
        val attachmentList = lawyerQuery.qDetails.questionAttachmentsList

        viewModelScope.launch {
            mainEvent.send(MainActivityEvent.AddingCommentsAndRepliesToAdapter(comments,commentsReplies, questionDetail))
            mainEvent.send(MainActivityEvent.AddingAttachmentToAdapter(attachmentList))
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
    }
}