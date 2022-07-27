package kr.mashup.ladder.config.interceptor

import kr.mashup.ladder.config.resolver.MEMBER_ID
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.session.Session
import org.springframework.session.SessionRepository
import org.springframework.stereotype.Component

@Component
class WsAuthInterceptor(
    private val sessionRepository: SessionRepository<out Session>,
) : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)
        when (accessor.command) {
            StompCommand.CONNECT -> {
                val sessionId = accessor.getSessionIdFromHeader()
                val memberId: Long = sessionRepository.findById(sessionId)?.getAttribute(MEMBER_ID)
                    ?: throw IllegalArgumentException()
                accessor.setMemberIdFromSessionAttributes(memberId)
            }
            else -> {}
        }

        return message
    }
}
