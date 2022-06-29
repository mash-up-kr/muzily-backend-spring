package kr.mashup.ladder.auth.dto.request

import kr.mashup.ladder.domain.account.domain.SocialType

data class AuthRequest(
    val socialType: SocialType,
    val code: String,
    val redirectUri: String,
)
