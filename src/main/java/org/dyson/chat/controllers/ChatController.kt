package org.dyson.chat.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.dyson.chat.entities.Chat
import org.dyson.chat.repositories.ChatRepository
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

    @PostMapping
    fun create(@RequestBody chat: Chat): ResponseEntity<Void> {
        chatRepository.save(chat)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun list(@RequestParam @ParameterObject pageable: Pageable): ResponseEntity<Page<Chat>> {
        return ResponseEntity.ok(chatRepository.findAll(pageable))
    }

    fun one() {

    }
}