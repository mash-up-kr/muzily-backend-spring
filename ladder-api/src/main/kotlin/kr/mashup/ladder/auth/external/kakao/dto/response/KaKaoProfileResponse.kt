package kr.mashup.ladder.auth.external.kakao.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KaKaoInfoResponse(
    val id: String,
    val kakaoAccount: KaKaoAccountResponse,
)


data class KaKaoAccountResponse(
    val email: String?,
    val profile: KaKaoProfileResponse,
)


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KaKaoProfileResponse(
    val nickname: String,
    val profileImage: String? = null,
)
