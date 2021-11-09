package com.example.chat.presentation.ui.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.presentation.model.MessageType

class ChatViewHolderEmpty(itemView: View) : AbsChatViewHolder(itemView) {

    override val itemType = MessageType.UNKNOWN

    fun bind() {

    }
}