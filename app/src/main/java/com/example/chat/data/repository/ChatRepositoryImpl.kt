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

    private val messageText: Flow<String> = channelFlow {
        repeat(100) {
            delay(1000)
            send(UUID.randomUUID().toString())
        }
    }
    private val messageId: Flow<String> = channelFlow {
        repeat(100) {
            delay(1300)
            send(UUID.randomUUID().toString())
        }
    }

    override suspend fun observe(): Flow<Chat> = combine(messageText, messageId) { text, id ->
        /**
         * проделываем операции с потоками, объединяем
         */
        Chat(id = id + text, time = System.currentTimeMillis())
    }

}