package kr.mashup.ladder.config.ws

import kr.mashup.ladder.config.context.AuthContext
import kr.mashup.ladder.config.context.AuthContextUtils
import kr.mashup.ladder.config.resolver.MEMBER_ID
import kr.mashup.ladder.domain.common.error.model.UnAuthorizedException
import kr.mashup.ladder.domain.member.infra.jpa.MemberRepository
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.session.Session
import org.springframework.session.SessionRepository
import org.springframework.stereotype.Component

private const val HEADER_TOKEN_PREFIX = "Bearer "

@Component
class WsAuthCheckInterceptor(
    private val memberRepository: MemberRepository,
    private val sessionRepository: SessionRepository<out Session>,
) : ChannelInterceptor {

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val authorization: String? = message.headers["Authorization"] as String?
        if (authorization.isNullOrBlank() || !authorization.startsWith(HEADER_TOKEN_PREFIX)) {
            throw UnAuthorizedException("Bearer 형식이 아닌 헤더 (${authorization})입니다.")
        }
        val sessionId = authorization.split(HEADER_TOKEN_PREFIX)[1]
        val memberId: Long? = findSessionBySessionId(sessionId).getAttribute(MEMBER_ID)

        if (memberId != null && memberRepository.existsMemberById(memberId)) {
            AuthContextUtils.set(AuthContext.of(memberId))
            return message
        }
        throw UnAuthorizedException("유효하지 않은 세션($sessionId)의 멤버(${memberId}) 입니다.")
    }

    private fun findSessionBySessionId(sessionId: String): Session {
        return sessionRepository.findById(sessionId)
            ?: throw UnAuthorizedException("해당하는 세션($sessionId)은 존재하지 않습니다")
    }

    override fun postSend(message: Message<*>, channel: MessageChannel, sent: Boolean) {
        AuthContextUtils.remove()
    }

}
