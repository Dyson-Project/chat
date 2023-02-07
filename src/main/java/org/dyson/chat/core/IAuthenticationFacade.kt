package org.dyson.chat.core

import org.springframework.security.core.Authentication

interface IAuthenticationFacade {
    val authentication: Authentication?
}
