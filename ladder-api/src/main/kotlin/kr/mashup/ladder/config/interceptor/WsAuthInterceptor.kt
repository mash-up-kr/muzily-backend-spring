package kr.mashup.ladder.config.interceptor

import org.springframework.http.HttpHeaders
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
        if (accessor.command != StompCommand.CONNECT) {
            return message
        }

        val header = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER_KEY)
        if (header.isNullOrBlank() || !header.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
            throw IllegalArgumentException()
        }

        val sessionId = header.split(AUTHORIZATION_HEADER_VALUE_PREFIX)[1]
        val memberId: Long = sessionRepository.findById(sessionId)?.getAttribute(MEMBER_ID)
            ?: throw IllegalArgumentException()
        if (accessor.sessionAttributes != null) {
            accessor.sessionAttributes!![MEMBER_ID] = memberId
        } else {
            accessor.sessionAttributes = mutableMapOf<String, Any>(Pair(MEMBER_ID, memberId))
        }

        return message
    }

    companion object {
        private const val AUTHORIZATION_HEADER_KEY = HttpHeaders.AUTHORIZATION
        private const val AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer "
        private const val MEMBER_ID = "MEMBER_ID"
    }
}
