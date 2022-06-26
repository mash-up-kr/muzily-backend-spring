package kr.mashup.ladder.external.kakao.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.SocialType

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KaKaoInfoResponse(
    val id: String,
    val kakaoAccount: KaKaoAccoutResponse,
) {

    fun toAccount(): Account {
        return Account.of(
            socialType = SocialType.KAKAO,
            socialId = id,
            nickname = kakaoAccount.profile.nickname,
            profileUrl = kakaoAccount.profile.profileImage,
        )
    }

}


data class KaKaoAccoutResponse(
    val email: String,
    val profile: KaKaoProfileResponse,
)


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KaKaoProfileResponse(
    val nickname: String,
    val profileImage: String? = null,
)
