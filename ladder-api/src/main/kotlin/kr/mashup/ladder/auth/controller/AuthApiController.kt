package kr.mashup.ladder.auth.controller

import kr.mashup.ladder.auth.dto.request.AuthRequest
import kr.mashup.ladder.auth.dto.response.LoginResponse
import kr.mashup.ladder.auth.service.AuthService
import kr.mashup.ladder.auth.service.AuthServiceFinder
import kr.mashup.ladder.common.dto.response.ApiResponse
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.resolver.ACCOUNT_ID
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession

@RestController
class AuthApiController(
    private val authServiceFinder: AuthServiceFinder,
    private val httpSession: HttpSession,
) {

    @PostMapping("/api/v1/auth")
    fun handleAuthentication(
        @RequestBody request: AuthRequest,
    ): ApiResponse<LoginResponse> {
        val authService: AuthService = authServiceFinder.getService(request.socialType)
        val accountId = authService.authentication(request)
        httpSession.setAttribute(ACCOUNT_ID, accountId)

        return ApiResponse.ok(
            LoginResponse(
                token = httpSession.id
            )
        )
    }

    @Auth
    @PostMapping("/api/v1/logout")
    fun logout(): ApiResponse<String> {
        httpSession.invalidate()
        return ApiResponse.OK
    }

}
