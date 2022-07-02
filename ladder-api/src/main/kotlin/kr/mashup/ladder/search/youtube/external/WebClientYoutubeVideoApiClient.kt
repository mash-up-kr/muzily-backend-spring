package kr.mashup.ladder.search.youtube.external

import kr.mashup.ladder.domain.common.error.model.BadGatewayException
import kr.mashup.ladder.search.youtube.external.dto.error.YoutubeVideoNotFoundException
import kr.mashup.ladder.search.youtube.external.dto.properties.YoutubeVideoApiProperties
import kr.mashup.ladder.search.youtube.external.dto.response.YoutubeVideoListResponse
import kr.mashup.ladder.search.youtube.external.dto.response.YoutubeVideoResponse
import kr.mashup.ladder.search.youtube.external.dto.type.YoutubeVideoCategory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.util.retry.Retry
import java.time.Duration

@Component
class WebClientYoutubeVideoApiClient(
    private val webClient: WebClient,
    private val youtubeVideoApiProperties: YoutubeVideoApiProperties,
) : YoutubeVideoApiClient {

    override fun getVideoInfo(videoId: String): YoutubeVideoResponse {
        val response = webClient.get()
            .uri(UriComponentsBuilder.fromUriString(youtubeVideoApiProperties.url)
                .queryParam("id", videoId)
                .queryParam("key", youtubeVideoApiProperties.key)
                .queryParam("part", youtubeVideoApiProperties.part)
                .queryParam("videoCategoryId", YoutubeVideoCategory.MUSIC.categoryId)
                .build()
                .toUriString()
            )
            .retrieve()
            .onStatus({ status -> status.is5xxServerError }, { response ->
                response.bodyToMono(String::class.java).map { message -> BadGatewayException(message) }
            })
            .bodyToMono(YoutubeVideoListResponse::class.java)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)).filter { throwable -> throwable is BadGatewayException })
            .block()

        return response?.items?.get(0)
            ?: throw YoutubeVideoNotFoundException("해당하는 Id(${videoId})를 가진 음악 영상은 존재하지 않습니다")
    }

}
