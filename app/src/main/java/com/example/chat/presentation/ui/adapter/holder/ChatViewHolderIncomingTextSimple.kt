package com.example.chat.presentation.ui.adapter.holder

import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.presentation.model.MessageType
import com.example.chat.presentation.model.chat.AbsMessage
import com.example.chat.presentation.model.chat.SimpleUserMessage
import com.example.chat.presentation.ui.utils.CardDrawable
import com.example.chat.utils.dp

class ChatViewHolderIncomingTextSimple(itemView: View) : AbsChatViewHolder(itemView) {

    override val itemType = MessageType.INCOMING_TEXT

    private val textView = itemView.findViewById<TextView>(R.id.tv_text)
    private val flBackground = itemView.findViewById<FrameLayout>(R.id.fl_message_content)

    fun bind(item: SimpleUserMessage) {
        textView.text = item.text
        textView.setTextColor(item.textColor)
        val drawable = CardDrawable.create(
            backgroundColors = item.backgroundColors,
            cornerRadius = item.cornersRadius,
            rippleColor = Color.GREEN
        )
        flBackground.background = drawable
    }
}