package com.example.chat.presentation.ui.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.presentation.model.MessageType

abstract class AbsChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract val itemType: MessageType
}