package kr.mashup.ladder.config.ws

import kr.mashup.ladder.config.handler.DetermineUserHandshakeHandler
import kr.mashup.ladder.config.interceptor.WsAuthInterceptor
import kr.mashup.ladder.config.interceptor.WsMemberPrincipalContextManageInterceptor
import kr.mashup.ladder.config.interceptor.WsRoomSessionContextManageInterceptor
import kr.mashup.ladder.config.resolver.WsMemberIdResolver
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

const val WS_ENDPOINT = "ws"
const val WS_DESTINATION_PREFIX_TOPIC = "/topic"
const val WS_DESTINATION_PREFIX_QUEUE = "/queue"
const val WS_APP_DESTINATION_PREFIX = "/app"
const val WS_USER_DESTINATION_PREFIX = "/user"

@Configuration
@EnableWebSocketMessageBroker
class WsConfig(
    private val determineUserHandshakeHandler: DetermineUserHandshakeHandler,
    private val wsAuthInterceptor: WsAuthInterceptor,
    private val wsRoomSessionContextManageInterceptor: WsRoomSessionContextManageInterceptor,
    private val wsMemberPrincipalContextManageInterceptor: WsMemberPrincipalContextManageInterceptor,
    private val wsMemberIdResolver: WsMemberIdResolver,
) : WebSocketMessageBrokerConfigurer {
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint(WS_ENDPOINT)
            .setHandshakeHandler(determineUserHandshakeHandler)
            .setAllowedOriginPatterns("*")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(
            wsAuthInterceptor,
            wsMemberPrincipalContextManageInterceptor,
            wsRoomSessionContextManageInterceptor)
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker(WS_DESTINATION_PREFIX_TOPIC, WS_DESTINATION_PREFIX_QUEUE)
        registry.setApplicationDestinationPrefixes(WS_APP_DESTINATION_PREFIX)
        registry.setUserDestinationPrefix(WS_USER_DESTINATION_PREFIX)
    }

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(wsMemberIdResolver)
    }
}
