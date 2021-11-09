package com.example.chat.data.repository

import android.util.Log
import com.example.chat.contract.ChatRepository
import com.example.chat.data.model.Chat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import java.util.*

class ChatRepositoryImpl : ChatRepository {

    /**
     * Получаем информацию из цетрефуги, при необходимости объединяем потоки
     */

    private val messageTime: Flow<Long> = channelFlow {
        repeat(100) {
            delay(1000)
            send(System.currentTimeMillis())
        }
    }
    private val messageText: Flow<String> = channelFlow {
        repeat(100) {
            delay(1300)
            send(UUID.randomUUID().toString())
        }
    }

    override suspend fun observe(): Flow<Chat> = combine(messageTime, messageText) { time, text ->
        /**
         * проделываем операции с потоками, объединяем
         */
        Log.i("shikleev", "ChatRepositoryImpl.observe: ")
        Chat(id = (time.toString() + text), time = time)
    }

}