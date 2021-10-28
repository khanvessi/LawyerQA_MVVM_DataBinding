package com.example.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.legalqa.CommentAttachment
import com.example.legalqa.databinding.CommentUploadAttachmentItemBinding

class CommentUploadAttachmentAdapter(private val commentUploadAttachmentList: List<CommentAttachment>) :
    RecyclerView.Adapter<CommentUploadAttachmentAdapter.CommentUploadAttachmentViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentUploadAttachmentViewHolder {
        val binding = CommentUploadAttachmentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommentUploadAttachmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentUploadAttachmentViewHolder, position: Int) {
        val currentItem = commentUploadAttachmentList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return commentUploadAttachmentList.size
    }

    class CommentUploadAttachmentViewHolder(private val binding: CommentUploadAttachmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: CommentAttachment) {
            binding.apply {
                commentsAttachment = currentItem
            }
        }

    }
}