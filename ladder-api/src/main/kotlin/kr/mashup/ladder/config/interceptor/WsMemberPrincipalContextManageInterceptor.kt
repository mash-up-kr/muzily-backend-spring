package kr.mashup.ladder.config.interceptor

import kr.mashup.ladder.config.context.WsMemberPrincipalContext
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

/**
 * WsConfig 에 interceptor 등록시, memberId 를 주입하는 WsAuthInterceptor 가 먼저 등록되어있어야 함
 *
 * @see kr.mashup.ladder.config.ws.WsConfig
 * @see kr.mashup.ladder.config.interceptor.WsAuthInterceptor
 */
@Component
class WsMemberPrincipalContextManageInterceptor : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)
        when (accessor.command) {
            StompCommand.CONNECT -> {
                val memberId = accessor.getMemberIdFromSessionAttributes()
                WsMemberPrincipalContext.add(memberId, accessor.user)
            }
            StompCommand.DISCONNECT -> {
                val memberId = accessor.getMemberIdFromSessionAttributes()
                WsMemberPrincipalContext.remove(memberId, accessor.user)
            }
            else -> {}
        }

        return message
    }
}
