package org.dyson.chat.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class MessagingService @Autowired constructor(val template: SimpMessagingTemplate){
    fun sendToUser(){

    }
    fun sendToGroup(){

    }

}