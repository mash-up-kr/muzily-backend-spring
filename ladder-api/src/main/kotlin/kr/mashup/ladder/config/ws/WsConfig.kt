package kr.mashup.ladder.config.ws

import kr.mashup.ladder.config.interceptor.WsAuthInterceptor
import kr.mashup.ladder.config.resolver.WsMemberIdResolver
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WsConfig(
    private val wsAuthInterceptor: WsAuthInterceptor,
    private val wsRedisMessageListenerManageInterceptor: WsRedisMessageListenerManageInterceptor,
    private val wsMemberIdResolver: WsMemberIdResolver,
) : WebSocketMessageBrokerConfigurer {
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("ws")
            .setAllowedOriginPatterns("*")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(wsAuthInterceptor, wsRedisMessageListenerManageInterceptor)
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/sub", "/queue")
    }

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(wsMemberIdResolver)
    }
}
