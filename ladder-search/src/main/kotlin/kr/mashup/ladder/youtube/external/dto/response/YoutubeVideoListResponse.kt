package kr.mashup.ladder.youtube.external.dto.response

import kr.mashup.ladder.youtube.model.YoutubeVideoCategory
import kr.mashup.ladder.youtube.model.exception.YoutubeNotAllowedCategoryException
import java.time.Duration
import java.time.LocalDateTime

data class YoutubeVideoListResponse(
    val items: List<YoutubeVideoResponse> = listOf(),
)

data class YoutubeVideoResponse(
    val id: String = "",
    val snippet: YoutubeSnippetResponse,
    val contentDetails: YoutubeContentDetailsResponse,
) {

    fun validateAllowedCategory() {
        if (!YoutubeVideoCategory.isMusicCategory(snippet.categoryId)) {
            throw YoutubeNotAllowedCategoryException("해당 Youtube 영상(${id})의 카테고리(${snippet.categoryId})는 허용되지 않았습니다")
        }
    }

}

data class YoutubeSnippetResponse(
    val publishedAt: LocalDateTime,
    val channelId: String = "",
    val title: String = "",
    val description: String = "",
    val categoryId: Int = 0,
    val thumbnails: YoutubeThumbnailListResponse,
)

data class YoutubeThumbnailListResponse(
    val default: YoutubeThumbnailResponse? = null,
    val medium: YoutubeThumbnailResponse? = null,
    val high: YoutubeThumbnailResponse? = null,
    val standard: YoutubeThumbnailResponse? = null,
    val maxres: YoutubeThumbnailResponse? = null,
)

data class YoutubeThumbnailResponse(
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0,
)

data class YoutubeContentDetailsResponse(
    val duration: Duration = Duration.ZERO,
)
