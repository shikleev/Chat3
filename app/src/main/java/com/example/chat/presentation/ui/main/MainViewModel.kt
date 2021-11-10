package com.example.chat.presentation.ui.main

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.*
import com.example.chat.DiInjector
import com.example.chat.data.model.Chat
import com.example.chat.presentation.model.MessageType
import com.example.chat.presentation.model.chat.AbsMessage
import com.example.chat.presentation.model.chat.AbsMessageText
import com.example.chat.presentation.model.chat.SimpleUserMessage
import com.example.chat.presentation.model.ui.CornersRadius
import com.example.chat.presentation.model.user.UserInfo
import com.example.chat.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel constructor() : ViewModel() {

    private val chatRepository = DiInjector.getChatRepository()
    /**
     * Используем SortedMap для списка сообщений, где ключ - это айди сообщения.
     * Далее, сортируем список по времени.
     * Это нужно для того, чтобы при изменении контента сообщения (текст, фото и пр.) мы
     * подставляли это сообщение в ту же позицию по айди.
     */
    private val messagesSortedMap = mutableMapOf<String, AbsMessage>()
    val chat: LiveData<List<AbsMessage>> = MutableLiveData()
    val viewState: LiveData<ViewState<UserInfo>> = MutableLiveData()

    init {
        getUserInfo()
        observeChat()
    }

    private var firstMessageTextCounter = 0

    private fun observeChat() = viewModelScope.launch {
        chatRepository.observe()
            .flowOn(Dispatchers.IO)
            .collect {
                val isIncoming = it.time.toInt() % 2 == 0
                val message = it.toSimpleUserMessage(isIncoming)
                messagesSortedMap[message.id] = message
                val sortedMap = messagesSortedMap.toList().sortedBy { (_, value) -> value.time }.toMap()
                val messagesList = sortedMap.values.toList().reversed()
                // Имитируем изменения текста в первом айтеме списка
                (messagesList.lastOrNull() as? AbsMessageText)?.text = "text ${firstMessageTextCounter++}"
                chat.toMutable().postValue(messagesList)
            }
    }

    private fun getUserInfo()  = viewModelScope.launch(Dispatchers.IO) {
        viewState.toMutable().postProgress()
        delay(500)
        viewState.toMutable().postSuccess(UserInfo(id = "123_qwe", name = "User"))
        delay(10000)
        viewState.toMutable().postError()
    }

    private fun Chat.toSimpleUserMessage(isIncoming: Boolean): SimpleUserMessage {
        val backgroundColors: IntArray
        val cornersRadius: CornersRadius
        if (isIncoming) {
            backgroundColors = intArrayOf(Color.DKGRAY, Color.DKGRAY)
            cornersRadius = CornersRadius(4.dp, 20.dp, 16.dp, 8.dp)
        } else {
            backgroundColors = intArrayOf(Color.parseColor("#FF0086E1"), Color.parseColor("#FF1C77B5"))
            cornersRadius = CornersRadius(16.dp, 6.dp, 16.dp, 16.dp)
        }
        return SimpleUserMessage(
            time = this.time,
            id = this.id,
            type = if (isIncoming) MessageType.INCOMING_TEXT else MessageType.OUTGOING_TEXT,
            text = id.substring(0..((15..55).random())),
            backgroundColors = backgroundColors,
            textColor = Color.WHITE,
            cornersRadius,
        )
    }
}