package org.dyson.chat.repositories

import org.dyson.chat.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
}
