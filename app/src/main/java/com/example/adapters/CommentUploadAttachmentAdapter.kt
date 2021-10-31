package com.example.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.legalqa.CommentAttachment
import com.example.legalqa.databinding.CommentUploadAttachmentItemBinding

class CommentUploadAttachmentAdapter(private val commentUploadAttachmentList: List<CommentAttachment>, private val listenerCancel: AttachmentListener) :
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
        holder.bind(currentItem, listenerCancel, position)
    }

    override fun getItemCount(): Int {
        return commentUploadAttachmentList.size
    }

    class CommentUploadAttachmentViewHolder(private val binding: CommentUploadAttachmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: CommentAttachment, listenerCancel: AttachmentListener, pos: Int) {
            binding.apply {
                position = pos
                listener = listenerCancel
                commentsAttachment = currentItem
            }
        }
    }
}

class AttachmentListener(val clickListener: (pos: Int) -> Unit) {
    fun onRemoveImageClick(pos: Int) = clickListener(pos)
}
