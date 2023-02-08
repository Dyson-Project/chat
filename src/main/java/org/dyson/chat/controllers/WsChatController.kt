package org.dyson.chat.controllers

import org.dyson.chat.core.AuthenticationFacade
import org.dyson.chat.dtos.MessageDto
import org.dyson.chat.dtos.toMessage
import org.dyson.chat.entities.Chat
import org.dyson.chat.enums.ChatType
import org.dyson.chat.exceptions.Code
import org.dyson.chat.exceptions.WSException
import org.dyson.chat.repositories.ChatRepository
import org.dyson.chat.repositories.MemberRepository
import org.dyson.chat.repositories.MessageRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Controller

@Controller
class WsChatController @Autowired constructor(
    val messagingTemplate: SimpMessagingTemplate,
    val messageRepository: MessageRepository,
    val memberRepository: MemberRepository,
    val chatRepository: ChatRepository,
    val authFacade: AuthenticationFacade
) {
    //    SimpMessageType.MESSAGE
//    StompCommand.SEND
    val log = LoggerFactory.getLogger(WsChatController::class.java)

    @MessageMapping("/chat")
    fun send(genericMessage: GenericMessage<MessageDto>) {
//        GenericMessage [headers={
//            simpMessageType=MESSAGE,
//            stompCommand=SEND,
//            nativeHeaders={destination=[/app/chat], content-length=[35]},
//            simpSessionAttributes={},
//            simpHeartbeat=[J@23d3619d,
//                simpUser=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=646BDCBAED89D1598275EFD33AED5A53], Granted Authorities=[]], lookupDestination=/chat, simpSessionId=r1wcd2fh, simpDestination=/app/chat
//        }]
        log.info("message: {} {}", genericMessage.headers.keys, genericMessage.headers["simpUser"])
        val msg = genericMessage.payload
        /*
        1. Chat 1-1
        -> 1.1 Chua tao chat
        -> 1.2 Da tao chat
        2. Chat group
        -> 2.1 Group da tao
         */
        // 1.1
        when (msg.chatType) {
            ChatType.PERSONAL -> {
                var newChatId: Long? = null
                if (msg.chatId == null) {
                    val newChat = chatRepository.save(
                        Chat(
                            "{}{}".format(msg.username),
                            ChatType.PERSONAL
                        )
                    )
                    msg.chatId = newChat.id
                }

                messageRepository.save(msg.toMessage())

                messagingTemplate.convertAndSendToUser(msg.username, "/messages", "")
            }

            ChatType.GROUP -> {
                val chatOpt = chatRepository.findById(msg.chatId)
                chatOpt.orElseThrow { throw WSException(Code.ID_NOT_FOUND) }
                messageRepository.save(msg.toMessage())

                messagingTemplate.convertAndSend("/chat", "")
            }
        }
    }


}
