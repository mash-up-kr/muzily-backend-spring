package kr.mashup.ladder.auth.controller

import io.swagger.annotations.ApiOperation
import kr.mashup.ladder.auth.dto.request.AuthRequest
import kr.mashup.ladder.auth.dto.response.LoginResponse
import kr.mashup.ladder.auth.service.AnonymousAuthService
import kr.mashup.ladder.auth.service.AuthService
import kr.mashup.ladder.auth.service.AuthServiceFinder
import kr.mashup.ladder.common.constants.ApiResponseConstants
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.resolver.MEMBER_ID
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession
import javax.validation.Valid

@RestController
class AuthApiController(
    private val authServiceFinder: AuthServiceFinder,
    private val anonymousAuthService: AnonymousAuthService,
    private val httpSession: HttpSession,
) {

    @ApiOperation("소셜 로그인/회원가입을 요청합니다")
    @PostMapping("/api/v1/auth")
    fun handleAuthentication(
        @Valid @RequestBody request: AuthRequest,
    ): LoginResponse {
        val authService: AuthService = authServiceFinder.getService(request.socialType)
        val memberId = authService.authentication(request)
        httpSession.setAttribute(MEMBER_ID, memberId)
        return LoginResponse(memberId = memberId, token = httpSession.id)
    }

    @ApiOperation("게스트 로그인을 위한 토큰을 요청합니다")
    @PostMapping("/api/v1/auth/anonymous")
    fun createAnonymousMember(): LoginResponse {
        val memberId = anonymousAuthService.createAnonymousMember()
        httpSession.setAttribute(MEMBER_ID, memberId)
        return LoginResponse(memberId = memberId, token = httpSession.id)
    }

    @ApiOperation("로그아웃을 요청합니다")
    @Auth
    @PostMapping("/api/v1/logout")
    fun logout(): String {
        httpSession.invalidate()
        return ApiResponseConstants.SUCCESS
    }

}
