package kr.mashup.ladder.config.interceptor

import kr.mashup.ladder.common.exception.model.ForbiddenNotAllowedAnonymousException
import kr.mashup.ladder.common.exception.model.UnAuthorizedException
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.resolver.MEMBER_ID
import kr.mashup.ladder.domain.member.domain.AccountConnectType
import kr.mashup.ladder.domain.member.infra.jpa.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpHeaders
import org.springframework.session.Session
import org.springframework.session.SessionRepository
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


private const val HEADER_TOKEN_PREFIX = "Bearer "

@Component
class AuthInterceptor(
    private val memberRepository: MemberRepository,
    private val sessionRepository: SessionRepository<out Session>,
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            return true
        }
        val auth = handler.getMethodAnnotation(Auth::class.java) ?: return true

        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)
        val memberId: Long? = getMemberId(authorization)
        if (memberId == null) {
            if (auth.optionalAuth) {
                return true
            }
            throw UnAuthorizedException("유효하지 않은 Authorization 헤더 (${authorization}) 입니다.")
        }

        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw UnAuthorizedException("유효하지 않은 세션의 멤버ID (${memberId}) 입니다.")

        if (!auth.allowedAnonymous) {
            if (AccountConnectType.CONNECTED == member.accountConnectType) {
                request.setAttribute(MEMBER_ID, memberId)
                return true
            }
            throw ForbiddenNotAllowedAnonymousException("멤버($memberId)는 게스트 사용자입니다. 계정에 연결된 멤버만이 접근할 수 있습니다")
        }
        request.setAttribute(MEMBER_ID, memberId)
        return true
    }

    private fun getMemberId(header: String?): Long? {
        if (header.isNullOrBlank() || !header.startsWith(HEADER_TOKEN_PREFIX)) {
            return null
        }
        val sessionId = header.split(HEADER_TOKEN_PREFIX)[1]
        return findSessionBySessionId(sessionId)?.getAttribute(MEMBER_ID)
    }

    private fun findSessionBySessionId(sessionId: String): Session? {
        return sessionRepository.findById(sessionId)
    }

}
