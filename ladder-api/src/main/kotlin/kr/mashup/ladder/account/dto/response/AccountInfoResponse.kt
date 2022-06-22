package kr.mashup.ladder.account.dto.response

import kr.mashup.ladder.domain.account.domain.Account

data class AccountInfoResponse(
    val nickname: String,
    val profileUrl: String?,
) {

    companion object {
        fun of(account: Account): AccountInfoResponse {
            return AccountInfoResponse(
                nickname = account.nickname,
                profileUrl = account.profileUrl
            )
        }
    }

}
