package com.example.chat.contract

import com.example.chat.data.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun observe(): Flow<Chat>
}