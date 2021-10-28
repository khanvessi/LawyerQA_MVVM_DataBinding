package com.example.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.adapters.ImageViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.legalqa.QuestionAttachments
import com.example.legalqa.databinding.ImageItemsBinding

class QuestionAttachmentAdapter(private val listOfQuestionsAttachments: List<QuestionAttachments>):
    RecyclerView.Adapter<QuestionAttachmentAdapter.QuestionAttachmentsViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionAttachmentAdapter.QuestionAttachmentsViewHolder {
        val binding = ImageItemsBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)

        return QuestionAttachmentsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: QuestionAttachmentAdapter.QuestionAttachmentsViewHolder,
        position: Int
    ) {
        val currentItem = listOfQuestionsAttachments[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return listOfQuestionsAttachments.size
    }

    class QuestionAttachmentsViewHolder(private val binding: ImageItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: QuestionAttachments) {
            binding.apply {
                attachments = currentItem
            }
        }


    }

}