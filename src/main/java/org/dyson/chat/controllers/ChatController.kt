package org.dyson.chat.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.dyson.chat.entities.Chat
import org.dyson.chat.repositories.ChatRepository
import org.slf4j.LoggerFactory
import org.springdoc.api.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@SecurityRequirement(name = "basic")
@RequestMapping("/api/v1/chats")
class ChatController @Autowired constructor(
    val chatRepository: ChatRepository
) {
    val log = LoggerFactory.getLogger(ChatController::class.java)

    @PostMapping
    fun create(@RequestBody chat: Chat): ResponseEntity<Void> {
        chatRepository.save(chat)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun list(@ParameterObject pageable: Pageable): ResponseEntity<Page<Chat>> {
        log.info("page {}", pageable)
        return ResponseEntity.ok(chatRepository.findAll(pageable))
    }

}