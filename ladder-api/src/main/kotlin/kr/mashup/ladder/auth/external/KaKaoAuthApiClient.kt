package kr.mashup.ladder.auth.external

import kr.mashup.ladder.auth.external.dto.response.KaKaoTokenResponse
import kr.mashup.ladder.auth.external.kakao.dto.response.KaKaoInfoResponse

interface KaKaoAuthApiClient {

    fun getAccessToken(code: String, redirectUri: String): KaKaoTokenResponse

    fun getProfileInfo(accessToken: String): KaKaoInfoResponse

}
