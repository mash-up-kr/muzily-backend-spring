package kr.mashup.ladder.search.youtube.external

import kr.mashup.ladder.search.youtube.external.dto.response.YoutubeVideoResponse

interface YoutubeVideoApiClient {

    fun getVideoInfo(videoId: String): YoutubeVideoResponse

}
