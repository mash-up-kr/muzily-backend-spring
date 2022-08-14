package kr.mashup.ladder.mood.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.mood.domain.MoodRecommend

data class MoodRecommendResponse(
    val recommendId: Long,
    val description: String,
) : BaseTimeResponse() {

    companion object {
        fun of(moodRecommend: MoodRecommend): MoodRecommendResponse {
            val response = MoodRecommendResponse(
                recommendId = moodRecommend.id,
                description = moodRecommend.description,
            )
            response.setBaseTime(moodRecommend)
            return response
        }
    }

}
