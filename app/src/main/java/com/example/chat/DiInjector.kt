package com.example.chat

import com.example.chat.contract.ChatRepository
import com.example.chat.data.repository.ChatRepositoryImpl

object DiInjector {

    fun getChatRepository(): ChatRepository = ChatRepositoryImpl()
}