package org.dyson.chat.repositories

import org.dyson.chat.entities.Message
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : MongoRepository<Message, Long>
