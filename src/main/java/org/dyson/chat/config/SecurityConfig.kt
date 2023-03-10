package org.dyson.chat.config

import org.dyson.chat.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
open class SecurityConfig @Autowired constructor(val userService: UserService) {
    val log = LoggerFactory.getLogger(SecurityConfig::class.java)
    @Bean
    open fun webMvc():WebMvcConfigurer{
        return object: WebMvcConfigurer{
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .maxAge(3600)
                    .exposedHeaders("*")
            }
        }
    }
    @Bean
    open fun securityFilterChan(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .cors().and()
            .authorizeRequests()
            .antMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
            ).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().permitAll()
            .and()
            .httpBasic()
            .and()
            .logout().permitAll()
        return http.build()
    }

    @Bean
    open fun webSecurity(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().antMatchers()
        }
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

}
