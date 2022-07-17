package kr.mashup.ladder.auth

import kr.mashup.ladder.auth.dto.request.AuthRequest
import kr.mashup.ladder.domain.member.domain.SocialType

class AuthFixture {
    companion object {
        fun `인증 요청값`(): AuthRequest {
            return AuthRequest(SocialType.KAKAO, "code", redirectUri = "redirectUri")
        }
    }
}
