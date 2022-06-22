package kr.mashup.ladder.config.interceptor

import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.resolver.ACCOUNT_ID
import kr.mashup.ladder.domain.common.error.model.UnAuthorizedException
import org.springframework.http.HttpHeaders
import org.springframework.session.Session
import org.springframework.session.SessionRepository
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


private const val HEADER_TOKEN_PREFIX = "Bearer "

@Component
class AuthInterceptor(
    private val sessionRepository: SessionRepository<out Session>,
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            return true
        }
        handler.getMethodAnnotation(Auth::class.java) ?: return true

        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (StringUtils.hasText(header) && header.startsWith(HEADER_TOKEN_PREFIX)) {
            val sessionId = header.split(HEADER_TOKEN_PREFIX)[1]
            val accountId: Long = findSessionBySessionId(sessionId).getAttribute(ACCOUNT_ID)
            request.setAttribute(ACCOUNT_ID, accountId)
            return true
        }
        throw UnAuthorizedException("Authorization 헤더에 Bearer 형식이 아닌 (${header})이 요청되었습니다.")
    }

    private fun findSessionBySessionId(sessionId: String): Session {
        return sessionRepository.findById(sessionId)
            ?: throw UnAuthorizedException("해당하는 세션($sessionId)은 존재하지 않습니다")
    }

}
