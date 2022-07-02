package kr.mashup.ladder.search.youtube.external.dto.response

import java.time.Duration
import java.time.LocalDateTime

data class YoutubeVideoListResponse(
    val items: List<YoutubeVideoResponse> = listOf(),
)

data class YoutubeVideoResponse(
    val id: String = "",
    val snippet: YoutubeSnippetResponse,
    val contentDetails: YoutubeContentDetailsResponse,
)

data class YoutubeSnippetResponse(
    val publishedAt: LocalDateTime,
    val channelId: String = "",
    val title: String = "",
    val description: String = "",
    val categoryId: String = "",
    val thumbnails: YoutubeThumbnailListResponse,
)

data class YoutubeThumbnailListResponse(
    val default: YoutubeThumbnailResponse,
    val medium: YoutubeThumbnailResponse,
    val high: YoutubeThumbnailResponse,
    val standard: YoutubeThumbnailResponse,
    val maxres: YoutubeThumbnailResponse,
)

data class YoutubeThumbnailResponse(
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0,
)

data class YoutubeContentDetailsResponse(
    val duration: Duration = Duration.ZERO,
)
