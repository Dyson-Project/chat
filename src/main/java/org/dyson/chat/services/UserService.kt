package org.dyson.chat.services

import org.dyson.chat.entities.User
import org.dyson.chat.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
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
) : UserDetailsManager, GroupManager {
    val log = LoggerFactory.getLogger(UserService::class.java)
    val jdbc: JdbcUserDetailsManager;

    init {
        jdbc = JdbcUserDetailsManager(dataSource)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("Not yet implemented")
    }

    override fun createUser(user: UserDetails) {
        log.info("{}", user)
        userRepository.save(user as User);
    }

    override fun updateUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(username: String?) {
        TODO("Not yet implemented")
    }

    override fun changePassword(oldPassword: String?, newPassword: String?) {
        TODO("Not yet implemented")
    }

    override fun userExists(username: String?): Boolean {
        TODO("Not yet implemented")
    }


    override fun findAllGroups(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun findUsersInGroup(groupName: String?): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun createGroup(groupName: String?, authorities: MutableList<GrantedAuthority>?) {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(groupName: String?) {
        TODO("Not yet implemented")
    }

    override fun renameGroup(oldName: String?, newName: String?) {
        TODO("Not yet implemented")
    }

    override fun addUserToGroup(username: String?, group: String?) {
        TODO("Not yet implemented")
    }

    override fun removeUserFromGroup(username: String?, groupName: String?) {
        TODO("Not yet implemented")
    }

    override fun findGroupAuthorities(groupName: String?): MutableList<GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun addGroupAuthority(groupName: String?, authority: GrantedAuthority?) {
        TODO("Not yet implemented")
    }

    override fun removeGroupAuthority(groupName: String?, authority: GrantedAuthority?) {
        TODO("Not yet implemented")
    }
}
