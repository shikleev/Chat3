package com.example.chat.presentation.model.chat

import com.example.chat.presentation.model.MessageType
import com.example.chat.presentation.model.ui.CornersRadius

data class SimpleUserMessage(
    override val time: Long,
    override val id: String,
    override val type: MessageType,
    override var text: String?,
    val backgroundColors: IntArray,
    val textColor: Int,
    val cornersRadius: CornersRadius,
) : AbsMessageText(
    time,
    id,
    type,
    text,
    backgroundColors,
    textColor,
    cornersRadius,
)