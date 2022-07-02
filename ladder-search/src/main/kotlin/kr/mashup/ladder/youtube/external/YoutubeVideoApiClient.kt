package kr.mashup.ladder.youtube.external

import kr.mashup.ladder.youtube.external.dto.response.YoutubeVideoResponse

interface YoutubeVideoApiClient {

    suspend fun getVideoInfo(videoId: String): YoutubeVideoResponse

}
