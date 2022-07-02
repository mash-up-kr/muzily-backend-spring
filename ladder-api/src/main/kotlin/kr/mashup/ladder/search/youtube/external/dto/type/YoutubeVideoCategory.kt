package kr.mashup.ladder.search.youtube.external.dto.type

/**
 * https://gist.github.com/dgp/1b24bf2961521bd75d6c
 */
enum class YoutubeVideoCategory(
    private val description: String,
    val categoryId: Int,
) {

    MUSIC(description = "Music", categoryId = 10),

}