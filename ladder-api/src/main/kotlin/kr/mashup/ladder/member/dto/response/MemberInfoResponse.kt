package kr.mashup.ladder.member.dto.response

import kr.mashup.ladder.domain.member.domain.AccountConnectType
import kr.mashup.ladder.domain.member.domain.Member

data class MemberInfoResponse(
    val nickname: String,
    val profileUrl: String?,
    val accountConnectType: AccountConnectType,
) {

    companion object {
        fun of(member: Member): MemberInfoResponse {
            return MemberInfoResponse(
                nickname = member.nickname,
                profileUrl = member.profileUrl,
                accountConnectType = member.accountConnectType,
            )
        }
    }

}
