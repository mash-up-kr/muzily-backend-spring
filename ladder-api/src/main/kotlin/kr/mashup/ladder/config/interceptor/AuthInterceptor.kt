package kr.mashup.ladder.config.interceptor

import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.resolver.MEMBER_ID
import kr.mashup.ladder.domain.common.error.model.NeedAccountForbiddenException
import kr.mashup.ladder.domain.common.error.model.UnAuthorizedException
import kr.mashup.ladder.domain.member.infra.jpa.MemberRepository
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

        val header: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header.isNullOrBlank() || !header.startsWith(HEADER_TOKEN_PREFIX)) {
            throw UnAuthorizedException("Bearer 형식이 아닌 헤더 (${header})입니다.")
        }

        val sessionId = header.split(HEADER_TOKEN_PREFIX)[1]
        val memberId: Long? = findSessionBySessionId(sessionId).getAttribute(MEMBER_ID)

        if (memberId != null && passCheckAuth(auth = auth, memberId = memberId)) {
            request.setAttribute(MEMBER_ID, memberId)
            return true
        }
        throw UnAuthorizedException("유효하지 않은 세션($sessionId)의 멤버(${memberId}) 입니다.")
    }

    private fun passCheckAuth(auth: Auth, memberId: Long): Boolean {
        when (auth.allowedAnonymous) {
            true -> {
                if (memberRepository.existsMemberById(memberId)) {
                    return true
                }
            }
            false -> {
                if (memberRepository.existsMemberHasAccountById(memberId)) {
                    return true
                }
                if (memberRepository.existsMemberById(memberId)) {
                    throw NeedAccountForbiddenException("멤버(${memberId})는 계정에 연결되지 않은 계정입니다. 계정에 연결된 멤버만이 접근할 수 있습니다")
                }
            }
        }
        return false
    }

    private fun findSessionBySessionId(sessionId: String): Session {
        return sessionRepository.findById(sessionId)
            ?: throw UnAuthorizedException("해당하는 세션($sessionId)은 존재하지 않습니다")
    }

}
