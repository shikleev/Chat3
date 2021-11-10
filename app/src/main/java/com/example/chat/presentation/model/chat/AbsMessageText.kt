package com.example.chat.presentation.model.chat

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.chat.presentation.model.MessageType
import com.example.chat.presentation.model.ui.CornersRadius

// В этом классе нет привязки к юзеру, мы можем использовать сервисные сообщения по-центру
// и прочие кастомные текстовые сообщения внутри чата
abstract class AbsMessageText(
    time: Long,
    id: String,
    type: MessageType,
    open var text: String?,
    backgroundColors: IntArray?,
    @ColorRes textColor: Int?,
    cornersRadius: CornersRadius?,
) : AbsMessage(time, id, type)