package kr.mashup.ladder.auth.dto.response

data class LoginResponse(
    val memberId: Long,
    val token: String,
)
