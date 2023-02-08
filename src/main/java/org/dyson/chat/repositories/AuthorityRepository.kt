package org.dyson.chat.repositories

import org.dyson.chat.entities.Authority
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authority, String> {
    fun findAuthoritiesByUserUsername(username: String): MutableList<Authority>
}