package kr.mashup.ladder.youtube.model

/**
 * Youtube Video Category
 * https://gist.github.com/dgp/1b24bf2961521bd75d6c
 */
enum class YoutubeVideoCategory(
    private val description: String,
    val categoryId: Int,
) {

    FILM(description = "Film & Animation", categoryId = 1),
    MUSIC(description = "Music", categoryId = 10),
    VIDEO_BLOGGING(description = "Videoblogging", categoryId = 21),
    ENTERTAINMENT(description = "Entertainment", categoryId = 24),
    CLASSIC(description = "Classics", categoryId = 33),
    ;

    companion object {

        fun isMusicCategory(categoryId: Int): Boolean {
            return values().map { value -> value.categoryId }.contains(categoryId)
        }

    }

}
