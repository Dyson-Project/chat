package org.dyson.chat.dtos

import org.dyson.chat.entities.Message
import org.dyson.chat.enums.ChatType

class MessageDto(
    val chatType: ChatType,
    val username: String,
    var chatId: Long? = null,
    val content: String,
    val data: String
)

fun MessageDto.toMessage() = Message(
    username,
    chatId,
    content,
    data
)
