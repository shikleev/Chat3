package com.example.chat.presentation.model.chat

import com.example.chat.presentation.model.MessageType

// Горизонтальный разделитель. Замысел в том, чтобы не использовать
// DividerItemDecoration, а использовать свои кастомные разделители,
// которыми можно гибко управлять
abstract class AbsMessageDivider(
    override val time: Long,
    override val id: String,
    override val type: MessageType,
) : AbsMessage(time, id, type)