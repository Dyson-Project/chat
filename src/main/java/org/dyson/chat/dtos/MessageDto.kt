package org.dyson.chat.dtos

import org.dyson.chat.entities.Message
import org.dyson.chat.enums.ChatType

class MessageDto(
    val chatType: ChatType,
    val username: String,
    val chatId: Long? = null,
    val content: String,
    val data: String
)

fun MessageDto.toMessage(newChatId: Long? = null) = Message(
    username,
    chatId ?: newChatId,
    content,
    data
)
