package kr.mashup.ladder.external.kakao

import kr.mashup.ladder.external.kakao.dto.response.KaKaoTokenResponse
import kr.mashup.ladder.external.kakao.dto.response.KaKaoInfoResponse

interface KaKaoAuthApiClient {

    fun getAccessToken(code: String, redirectUri: String): KaKaoTokenResponse

    fun getProfileInfo(accessToken: String): KaKaoInfoResponse

}
