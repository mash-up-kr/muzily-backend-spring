package kr.mashup.ladder.member.dto.response

import kr.mashup.ladder.domain.member.domain.Member

data class MemberInfoResponse(
    val nickname: String,
    val profileUrl: String?,
) {

    companion object {
        fun of(member: Member): MemberInfoResponse {
            return MemberInfoResponse(
                nickname = member.nickname,
                profileUrl = member.profileUrl
            )
        }
    }

}
