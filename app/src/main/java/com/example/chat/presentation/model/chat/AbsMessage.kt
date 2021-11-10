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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AbsMessage
        if ((id + time) != (other.id + other.time)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + time.hashCode()
        return result
    }
}