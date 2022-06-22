package kr.mashup.ladder.account.dto.request

import javax.validation.constraints.NotBlank

data class UpdateAccountInfoRequest(
    @field:NotBlank(message = "닉네임을 입력해주세요")
    val nickname: String = "",

    val profileUrl: String? = null,
)
