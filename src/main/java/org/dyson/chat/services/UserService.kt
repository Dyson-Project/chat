package org.dyson.chat.services

import org.dyson.chat.entities.User
import org.dyson.chat.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.GroupManager
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class UserService @Autowired constructor(
    dataSource: DataSource,
    private val userRepository: UserRepository,
) : JdbcUserDetailsManager(dataSource), UserDetailsManager, GroupManager {
    val log = LoggerFactory.getLogger(UserService::class.java)
//    val jdbc: JdbcUserDetailsManager;
//
//    init {
//        jdbc = JdbcUserDetailsManager(dataSource)
//    }

    override fun createUser(user: UserDetails) {
        log.info("{}", user)
        userRepository.save(user as User);
    }


}
