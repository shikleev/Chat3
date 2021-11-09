package com.example.chat.presentation.model.chat

import com.example.chat.presentation.model.MessageType

// Базовый класс для всех типов ячеек внутри чата
abstract class AbsMessage(
    open val time: Long,
    open val id: String,
    open val type: MessageType,
) : Comparable<AbsMessage> {
    override fun compareTo(other: AbsMessage): Int {
        return if (time > other.time) 1
        else -1
    }
}