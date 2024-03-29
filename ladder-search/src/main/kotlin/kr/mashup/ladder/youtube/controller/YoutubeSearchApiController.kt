package kr.mashup.ladder.youtube.controller

import kr.mashup.ladder.youtube.external.YoutubeVideoApiClient
import kr.mashup.ladder.youtube.external.dto.response.YoutubeVideoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class YoutubeSearchApiController(
    private val youtubeVideoApiClient: YoutubeVideoApiClient,
) {

    @GetMapping("/v1/youtube/video")
    suspend fun searchYoutubeVideoInfo(
        @RequestParam videoId: String,
    ): YoutubeVideoResponse {
        val response = youtubeVideoApiClient.getVideoInfo(videoId = videoId)
        response.validateAllowedCategory()
        return response
    }

}
