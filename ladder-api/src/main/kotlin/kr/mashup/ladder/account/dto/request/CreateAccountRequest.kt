package kr.mashup.ladder.account.dto.request

import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.SocialType

data class CreateAccountRequest(
    val socialId: String,
    val socialType: SocialType,
    val nickname: String,
    val profileUrl: String?
) {

    fun toEntity(): Account {
        return Account.of(
            socialType = socialType,
            socialId = socialId,
            nickname = nickname,
            profileUrl = profileUrl
        )
    }

}
