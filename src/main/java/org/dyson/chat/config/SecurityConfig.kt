package org.dyson.chat.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource


@Configuration
open class SecurityConfig {
    @Bean
    open fun securityFilterChan(http: HttpSecurity): SecurityFilterChain {

        return http.build();
    }

    @Bean
    open fun webSecurity(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().antMatchers()
        }
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    open fun userDetailsManager(): UserDetailsManager {
//        val user = User.withUsername("user")
//            .username("root")
//            .password(BCryptPasswordEncoder().encode("root"))
//            .roles("ROOT")
//            .build()
//        val users = JdbcUserDetailsManager(null)
//        users.createUser(user);
//        return users;
//    }
}
