package com.example.chat.presentation.ui.main

import android.graphics.Color
import androidx.lifecycle.*
import com.example.chat.contract.ChatRepository
import com.example.chat.data.model.Chat
import com.example.chat.data.repository.ChatRepositoryImpl
import com.example.chat.presentation.model.MessageType
import com.example.chat.presentation.model.chat.AbsMessage
import com.example.chat.presentation.model.chat.SimpleUserMessage
import com.example.chat.presentation.model.ui.CornersRadius
import com.example.chat.presentation.model.user.UserInfo
import com.example.chat.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val chatRepository: ChatRepository = ChatRepositoryImpl()
    private val messagesSortedSet = sortedSetOf<AbsMessage>()

    val chat: LiveData<List<AbsMessage>> = MutableLiveData()
    val viewState: LiveData<ViewState<UserInfo>> = MutableLiveData()

    init {
        getUserInfo()
        observeChat()
    }

    private fun observeChat() = viewModelScope.launch {
        chatRepository.observe()
            .flowOn(Dispatchers.IO)
            .collect {
                val isIncoming = it.time.toInt() % 2 == 0
                val message = it.toSimpleUserMessage(isIncoming)
                messagesSortedSet.add(message)
                chat.toMutable().postValue(messagesSortedSet.toList().reversed())
            }
    }

    private fun getUserInfo()  = viewModelScope.launch {
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
            backgroundColors = backgroundColors,
            textColor = Color.WHITE,
            cornersRadius,
            text = id.substring(0..((15..45).random()))
        )
    }
}