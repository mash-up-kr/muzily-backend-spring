package kr.mashup.ladder.youtube.external

import kr.mashup.ladder.youtube.external.dto.error.YoutubeVideoNotFoundException
import kr.mashup.ladder.youtube.external.dto.properties.YoutubeVideoApiProperties
import kr.mashup.ladder.youtube.external.dto.response.YoutubeVideoListResponse
import kr.mashup.ladder.youtube.external.dto.response.YoutubeVideoResponse
import kr.mashup.ladder.youtube.external.dto.type.YoutubeVideoCategory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder

@Component
class WebClientYoutubeVideoApiClient(
    private val webClient: WebClient,
    private val youtubeVideoApiProperties: YoutubeVideoApiProperties,
) : YoutubeVideoApiClient {

    override suspend fun getVideoInfo(videoId: String): YoutubeVideoResponse {
        val response: YoutubeVideoListResponse = webClient.get()
            .uri(UriComponentsBuilder.fromUriString(youtubeVideoApiProperties.url)
                .queryParam("id", videoId)
                .queryParam("key", youtubeVideoApiProperties.key)
                .queryParam("part", youtubeVideoApiProperties.part)
                .queryParam("videoCategoryId", YoutubeVideoCategory.MUSIC.categoryId)
                .build()
                .toUriString()
            )
            .retrieve()
            .awaitBody()

        if (response.items.isEmpty()) {
            throw YoutubeVideoNotFoundException("해당하는 Id(${videoId})를 가진 음악 영상은 존재하지 않습니다")
        }
        return response.items[0]
    }

}
