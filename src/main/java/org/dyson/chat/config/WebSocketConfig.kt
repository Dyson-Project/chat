package org.dyson.chat.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.server.support.DefaultHandshakeHandler


@Configuration
@EnableWebSocketMessageBroker
open class WebSocketConfig(
    @Value("\${websocket.replay.host}")
    val host: String,
    @Value("\${websocket.replay.port}")
    val port: Int,
    @Value("\${websocket.replay.login}")
    val login: String,
    @Value("\${websocket.replay.passcode}")
    val passcode: String,
) : AbstractSecurityWebSocketMessageBrokerConfigurer() {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableStompBrokerRelay("/topic/", "/queue")
            .setRelayHost(host)
            .setRelayPort(port)
            .setSystemLogin(login)
            .setSystemPasscode(passcode)
            .setClientLogin(login)
            .setClientPasscode(passcode)
        registry.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/connect")
        registry.addEndpoint("/connect").setHandshakeHandler(
            object : DefaultHandshakeHandler() {
                @Throws(Exception::class)
                fun beforeHandshake(
                    request: ServerHttpRequest, response: ServerHttpResponse?, wsHandler: WebSocketHandler?,
                    attributes: MutableMap<Any, Any>
                ): Boolean {
                    if (request is ServletServerHttpRequest) {
                        val session = request.servletRequest.session
                        attributes["sessionId"] = session.id
                    }
                    return true
                }
            }
        ).withSockJS()
    }

    override fun configureInbound(messages: MessageSecurityMetadataSourceRegistry?) {
        messages!!.nullDestMatcher().permitAll()
            .simpDestMatchers("/app/connect", "/app/chat**")
            .permitAll()
            .simpSubscribeDestMatchers("/topic/**", "/user/**")
            .permitAll()
            .anyMessage().permitAll()
    }

    override fun sameOriginDisabled(): Boolean {
        return true
    }

}
