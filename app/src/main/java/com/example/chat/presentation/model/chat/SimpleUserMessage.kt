package com.example.chat.presentation.model.chat

import com.example.chat.presentation.model.MessageType
import com.example.chat.presentation.model.ui.CornersRadius

data class SimpleUserMessage(
    override val time: Long,
    override val id: String,
    override val type: MessageType,
    val backgroundColors: IntArray,
    val textColor: Int,
    val cornersRadius: CornersRadius,
    val text: String?,
) : AbsMessageText(
    time,
    id,
    type,
    backgroundColors,
    textColor,
    cornersRadius,
)