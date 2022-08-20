package kr.mashup.ladder.auth.external.kakao

import kr.mashup.ladder.auth.model.exception.InvalidKaKaoTokenException
import kr.mashup.ladder.auth.external.kakao.dto.properties.KaKaoTokenProperties
import kr.mashup.ladder.auth.external.kakao.dto.properties.KaKaoUserProperties
import kr.mashup.ladder.auth.external.kakao.dto.response.KaKaoTokenResponse
import kr.mashup.ladder.auth.external.kakao.dto.response.KaKaoInfoResponse
import kr.mashup.ladder.common.exception.model.BadGatewayException
import kr.mashup.ladder.common.exception.model.UnknownErrorException
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.util.retry.Retry
import java.time.Duration

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
            .onStatus({ status -> status.is4xxClientError }, { response ->
                response.bodyToMono(String::class.java).map { message -> InvalidKaKaoTokenException(message) }
            })
            .onStatus({ status -> status.is5xxServerError }, { response ->
                response.bodyToMono(String::class.java).map { message -> BadGatewayException(message) }
            })
            .bodyToMono(KaKaoTokenResponse::class.java)
            .timeout(Duration.ofSeconds(3))
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)).filter { throwable -> throwable is BadGatewayException })
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
        val kaKaoProfileResponse: KaKaoInfoResponse? = webClient.get()
            .uri(kaKaoProfileProperties.url)
            .headers { headers -> headers.setBearerAuth(accessToken) }
            .retrieve()
            .onStatus({ status -> status.is4xxClientError }, { response ->
                response.bodyToMono(String::class.java).map { message -> UnknownErrorException(message) }
            })
            .onStatus({ status -> status.is5xxServerError }, { response ->
                response.bodyToMono(String::class.java).map { message -> BadGatewayException(message) }
            })
            .bodyToMono(KaKaoInfoResponse::class.java)
            .timeout(Duration.ofSeconds(3))
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)).filter { throwable -> throwable is BadGatewayException })
            .block()
        return kaKaoProfileResponse ?: throw UnknownErrorException("KaKaoInfoResponse can't be null")
    }

}
