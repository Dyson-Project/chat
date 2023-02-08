package org.dyson.chat.controllers

import org.dyson.chat.entities.Chat
import org.dyson.chat.repositories.ChatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/chats")
class ChatController @Autowired constructor(
    val chatRepository: ChatRepository
) {

    @PostMapping
    fun create(@RequestBody chat: Chat): ResponseEntity<Void> {
        chatRepository.save(chat);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    fun list(@RequestParam pageable: Pageable): ResponseEntity<Page<Chat>> {
        return ResponseEntity.ok(chatRepository.findAll(pageable));
    }

    fun one() {

    }
}