package kr.mashup.ladder.config.interceptor

import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.resolver.ACCOUNT_ID
import kr.mashup.ladder.domain.account.Account
import kr.mashup.ladder.domain.account.AccountRepository
import kr.mashup.ladder.domain.account.SocialType
import kr.mashup.ladder.domain.common.error.model.UnAuthorizedException
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val HEADER_TOKEN_PREFIX = "Bearer "

@Component
class AuthInterceptor(
    private val accountRepository: AccountRepository,
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            return true
        }
        handler.getMethodAnnotation(Auth::class.java) ?: return true

        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (StringUtils.hasText(header) && header.startsWith(HEADER_TOKEN_PREFIX)) {
            val token = header.split(HEADER_TOKEN_PREFIX)[1]

            // TODO: 인증 구현 JWT vs Session 후 제거
            val account = Account.of(
                socialId = "social-id",
                socialType = SocialType.KAKAO,
                nickname = "닉네임",
                profileUrl = "https://profile.png"
            )
            accountRepository.save(account)

            request.setAttribute(ACCOUNT_ID, account.id)
            return true
        }
        throw UnAuthorizedException("인증이 실패하였습니다 - 비거나 ($HEADER_TOKEN_PREFIX) 형식이 아닌 헤더(${header})가 요청되었습니다.")
    }

}
