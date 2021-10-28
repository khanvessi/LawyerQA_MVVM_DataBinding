package com.example.legalqa

import com.google.gson.annotations.SerializedName

data class LawyerQuery(
    @SerializedName("success")
    val success: String,
    @SerializedName("comment")
    val comment: Comment,
    @SerializedName("questionDetail")
    val qDetails: QuestionDetail,
) {
}
data class Comment(
    @SerializedName("comments")
    val comments: List<Comments>,
){

}

data class Comments(
    @SerializedName("commentId")
    val commentId: Int,
    @SerializedName("parentCommentId")
    val parentCommentId: Int,
    @SerializedName("questionId")
    val questionId: Int,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("commentBy")
    val commentBy: String,
    @SerializedName("commentById")
    val commentById: Int,
    @SerializedName("commentAt")
    val commentAt: String,
    @SerializedName("commentByUrl")
    val commentByUrl: String,
    @SerializedName("replyToId")
    val replyToId: Int,
    @SerializedName("replyToName")
    val replyToName: String,
    @SerializedName("commentAttachments")
    val commentAttachment: List<CommentAttachment>,
    @SerializedName("commentReplies")
    val commentReplies: List<CommentReplies>,
){

}

data class CommentAttachment(
    @SerializedName("questionAttachmentId")
    val questionAttachmentId: Int = 0,
    @SerializedName("commentId")
    val commentId: Int = 0,
    @SerializedName("attachmentUrl")
    var attachmentUrl: String = "",
    @SerializedName("isDocType")
    val isDocType: String = ""

){

}

data class CommentReplies(
    @SerializedName("commentId")
    val commentId: Int,
    @SerializedName("parentCommentId")
    val parentCommentId: Int,
    @SerializedName("questionId")
    val questionId: Int,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("commentBy")
    val commentBy: String,
    @SerializedName("commentById")
    val commentById: Int,
    @SerializedName("commentAt")
    val commentAt: String,
    @SerializedName("commentByUrl")
    val commentByUrl: String,
    @SerializedName("replyToId")
    val replyToId: Int,
    @SerializedName("replyToName")
    val replyToName: String

){

}

data class QuestionDetail(
    @SerializedName("questionId")
    val questionId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("postedAt")
    val postedAt: String,
    @SerializedName("postedBy")
    val postedBy: String,
    @SerializedName("postedById")
    val postedById: Int,
    @SerializedName("postedByProfileUrl")
    val postedByProfileUrl: String,
    @SerializedName("questionAttachments")
    val questionAttachmentsList: List<QuestionAttachments>
){

}

data class QuestionAttachments(
    @SerializedName("questionAttachmentId")
    val questionAttachmentId: Int,
    @SerializedName("attachmentUrl")
    val attachmentUrl: String,
    @SerializedName("isDocType")
    val isDocType: Int
){}
