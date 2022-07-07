package kr.mashup.ladder.auth.dto.request

import kr.mashup.ladder.domain.member.domain.SocialType
import javax.validation.constraints.NotBlank

data class AuthRequest(
    val socialType: SocialType,

    @NotBlank
    val code: String = "",

    @NotBlank
    val redirectUri: String = "",
)
