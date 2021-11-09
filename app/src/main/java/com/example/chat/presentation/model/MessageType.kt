package com.example.chat.presentation.model

import androidx.annotation.LayoutRes
import com.example.chat.R

/**
 * Здесь перечисляются все типы сообщений в чате
 * Главный смысл в том, что внутри ресайклера мы не используем отступы, цвета, разделители и пр.
 * Все определяется внутри каждого лэйаута, это обеспечит большую гибкость
 * для отрисовки сложных кастомных вьюх и анимаций.
 */
enum class MessageType(@LayoutRes val layoutId: Int) {
    UNKNOWN(R.layout.layout_chat_unknown),
    SIMPLE_TRANSPARENT_DIVIDER(R.layout.layout_chat_unknown),
    INCOMING_TEXT(R.layout.layout_chat_simple_incoming_text),
    OUTGOING_TEXT(R.layout.layout_chat_simple_outgoung_text),
    // ........ все типы сообщений здесь
    ;

    companion object {
        fun getTypeByHashCode(hashCode: Int): MessageType {
            return values().find { it.hashCode() == hashCode } ?: UNKNOWN
        }
    }
}