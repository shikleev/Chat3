package com.example.chat.presentation.ui.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.presentation.model.MessageType

class ChatViewHolderSimpleDivider(itemView: View) : AbsChatViewHolder(itemView) {

    override val itemType = MessageType.SIMPLE_TRANSPARENT_DIVIDER

    fun bind() {

    }
}