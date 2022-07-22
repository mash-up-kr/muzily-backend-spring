package kr.mashup.ladder.youtube.external

import kotlinx.coroutines.reactor.awaitSingle
import kr.mashup.ladder.domain.common.exception.model.BadGatewayException
import kr.mashup.ladder.youtube.model.exception.YoutubeVideoNotFoundException
import kr.mashup.ladder.youtube.external.dto.properties.YoutubeVideoApiProperties
import kr.mashup.ladder.youtube.external.dto.response.YoutubeVideoListResponse
import kr.mashup.ladder.youtube.external.dto.response.YoutubeVideoResponse
import kr.mashup.ladder.youtube.model.YoutubeVideoCategory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.util.retry.Retry
import java.time.Duration

/**
 * Youtube Videos list API
 * https://developers.google.com/youtube/v3/docs/videos/list?hl=ko
 */
@Component
class WebClientYoutubeVideoApiClient(
    private val webClient: WebClient,
    private val youtubeVideoApiProperties: YoutubeVideoApiProperties,
) : YoutubeVideoApiClient {

    // TODO: Add Cache
    override suspend fun getVideoInfo(videoId: String): YoutubeVideoResponse {
        return webClient.get()
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
            .cache(Duration.ofMinutes(1))
            .map { videoListResponse -> videoListResponse.items }
            .timeout(Duration.ofSeconds(3))
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)).filter { throwable -> throwable is BadGatewayException })
            .awaitSingle()
            .let { matchedVideos ->
                if (matchedVideos.isEmpty()) {
                    throw YoutubeVideoNotFoundException("해당하는 음악 카테고리의 Youtube 영상(${videoId})은 존재하지 않습니다")
                }
                matchedVideos.first()
            }
    }

}
