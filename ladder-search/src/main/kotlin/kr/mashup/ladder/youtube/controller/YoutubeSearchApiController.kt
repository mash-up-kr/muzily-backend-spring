package kr.mashup.ladder.youtube.controller

import kr.mashup.ladder.common.dto.response.ApiResponse
import kr.mashup.ladder.youtube.external.YoutubeVideoApiClient
import kr.mashup.ladder.youtube.external.dto.response.YoutubeVideoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class YoutubeSearchApiController(
    private val youtubeVideoApiClient: YoutubeVideoApiClient,
) {

    @GetMapping("/search/v1/youtube/video")
    suspend fun getVideoInfo(
        @RequestParam videoId: String,
    ): ApiResponse<YoutubeVideoResponse> {
        return ApiResponse.ok(youtubeVideoApiClient.getVideoInfo(videoId))
    }

}
