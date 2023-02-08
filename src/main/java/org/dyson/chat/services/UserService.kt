package org.dyson.chat.services

import org.dyson.chat.entities.Authority
import org.dyson.chat.entities.User
import org.dyson.chat.repositories.AuthorityRepository
import org.dyson.chat.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.provisioning.GroupManager
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class UserService @Autowired constructor(
    dataSource: DataSource,
    private val userRepository: UserRepository,
    private val authorityRepository: AuthorityRepository,
) : JdbcUserDetailsManager(dataSource), UserDetailsManager, GroupManager {
    val log = LoggerFactory.getLogger(UserService::class.java)

    override fun createUser(user: UserDetails) {
        log.info("{}", user)
        userRepository.save(user as User)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findById(username).orElseThrow {
            throw UsernameNotFoundException("Username {} not found".format(username))
        }
        val authSet = hashSetOf<GrantedAuthority>()
        if (this.enableAuthorities) {
            authSet.addAll(loadUserAuthorities(user.username))
        }
        if (this.enableGroups) {
            authSet.addAll(loadGroupAuthorities(user.username))
        }
        val dbAuths = authSet.toMutableList()
        addCustomAuthorities(username, dbAuths)

        return createUserDetails(username, user, dbAuths)
    }

    override fun loadUserAuthorities(username: String): MutableList<Authority> {
        return authorityRepository.findAuthoritiesByUserUsername(username)
    }

    override fun loadGroupAuthorities(username: String?): MutableList<GrantedAuthority> {
        return super.loadGroupAuthorities(username)
    }

    override fun addCustomAuthorities(username: String?, authorities: MutableList<GrantedAuthority>?) {
        super.addCustomAuthorities(username, authorities)
    }

    override fun createUserDetails(
        username: String,
        userFromUserQuery: UserDetails,
        combinedAuthorities: MutableList<GrantedAuthority>
    ): UserDetails {
        return User(
            username,
            userFromUserQuery.password,
            userFromUserQuery.isEnabled,
            userFromUserQuery.isAccountNonExpired,
            userFromUserQuery.isCredentialsNonExpired,
            userFromUserQuery.isAccountNonLocked,
            combinedAuthorities.map { it as Authority }.toMutableSet()
        )
    }
}
