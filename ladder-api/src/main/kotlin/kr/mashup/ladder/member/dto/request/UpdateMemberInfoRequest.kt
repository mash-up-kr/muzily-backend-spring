package kr.mashup.ladder.member.dto.request

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank

data class UpdateMemberInfoRequest(
    @field:NotBlank(message = "닉네임을 입력해주세요")
    val nickname: String = "",

    @field:URL(message = "URL 형식에 어긋납니다")
    val profileUrl: String? = null,
)
