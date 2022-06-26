package kr.mashup.ladder.external.kakao

import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.InvalidRequestException
import kr.mashup.ladder.domain.common.error.model.UnknownErrorException
import kr.mashup.ladder.external.kakao.dto.properties.KaKaoTokenProperties
import kr.mashup.ladder.external.kakao.dto.properties.KaKaoUserProperties
import kr.mashup.ladder.external.kakao.dto.response.KaKaoInfoResponse
import kr.mashup.ladder.external.kakao.dto.response.KaKaoTokenResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.rmi.activation.UnknownObjectException
import java.util.function.Consumer

// TODO: 에러 헨들링 추가
@Component
class WebClientKaKaoAuthApiClientImpl(
    private val webClient: WebClient,
    private val kaKaoTokenProperties: KaKaoTokenProperties,
    private val kaKaoProfileProperties: KaKaoUserProperties,
) : KaKaoAuthApiClient {

    override fun getAccessToken(code: String, redirectUri: String): KaKaoTokenResponse {
        val kaKaoTokenResponse: KaKaoTokenResponse? = webClient.post()
            .uri(kaKaoTokenProperties.url)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .body(BodyInserters.fromFormData(createAccessTokenRequest(code, redirectUri)))
            .retrieve()
            .onStatus(
                { obj: HttpStatus -> obj.is4xxClientError },
                {
                    Mono.error {
                        InvalidRequestException("잘못된 인증 토큰($code) redirectUri(${redirectUri}이 요청되었습니다",
                            ErrorCode.AUTH_TOKEN_INVALID)
                    }
                })
            .onStatus(
                { obj: HttpStatus -> obj.is5xxServerError },
                {
                    Mono.error { UnknownErrorException("카카오 액세스 토큰 API 호출 중 에러가 발생하였습니다") }
                })
            .bodyToMono(KaKaoTokenResponse::class.java)
            .block()

        return kaKaoTokenResponse ?: throw UnknownErrorException("KaKaoTokenResponse can't be null")
    }

    private fun createAccessTokenRequest(code: String, redirectUri: String): MultiValueMap<String, String> {
        val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
        formData.add("client_id", kaKaoTokenProperties.clientId)
        formData.add("client_secret", kaKaoTokenProperties.clientSecret)
        formData.add("grant_type", kaKaoTokenProperties.grantType)
        formData.add("code", code)
        formData.add("redirect_uri", redirectUri)
        return formData
    }

    override fun getProfileInfo(accessToken: String): KaKaoInfoResponse {
        println(accessToken)
        val kaKaoProfileResponse: KaKaoInfoResponse? = webClient.get()
            .uri(kaKaoProfileProperties.url)
            .headers(Consumer { headers ->
                headers.setBearerAuth(accessToken)
            })
            .retrieve()
            .onStatus(
                { obj: HttpStatus -> obj.is4xxClientError },
                {
                    Mono.error {
                        UnknownObjectException("잘못된 액세스 토큰($accessToken)이 요청되었습니다")
                    }
                })
            .onStatus(
                { obj: HttpStatus -> obj.is5xxServerError },
                {
                    Mono.error { UnknownErrorException("카카오 프로필 API 호출 중 에러가 발생하였습니다") }
                })
            .bodyToMono(KaKaoInfoResponse::class.java)
            .block()
        return kaKaoProfileResponse ?: throw UnknownErrorException("KaKaoInfoResponse can't be null")
    }

}
