package kr.mashup.ladder.member.dto.request

import javax.validation.constraints.NotBlank

data class UpdateMemberInfoRequest(
    @field:NotBlank(message = "닉네임을 입력해주세요")
    val nickname: String = "",

    val profileUrl: String? = null,
)
