package org.dyson.chat

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.SimpMessageTypeMessageCondition
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Controller
import java.awt.TrayIcon.MessageType

@Controller
class ChatController @Autowired constructor(
    val messagingTemplate: SimpMessagingTemplate
) {
    //    SimpMessageType.MESSAGE
//    StompCommand.SEND
    val log = LoggerFactory.getLogger(ChatController::class.java);

    @MessageMapping("/chat")
    @SendToUser("/messages")
    fun send(genericMessage: GenericMessage<Message>): Message {
//        GenericMessage [headers={
//            simpMessageType=MESSAGE,
//            stompCommand=SEND,
//            nativeHeaders={destination=[/app/chat], content-length=[35]},
//            simpSessionAttributes={},
//            simpHeartbeat=[J@23d3619d,
//                simpUser=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=646BDCBAED89D1598275EFD33AED5A53], Granted Authorities=[]], lookupDestination=/chat, simpSessionId=r1wcd2fh, simpDestination=/app/chat
//        }]
        log.info("message: {} {}", genericMessage.headers.keys, genericMessage.headers["simpUser"])
        return Message("form", "content")
    }


}