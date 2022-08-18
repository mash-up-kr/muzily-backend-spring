package kr.mashup.ladder.mood.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.mood.domain.MoodSuggestion

data class MoodSuggestionResponse(
    val suggestionId: Long,
    val name: String,
) : BaseTimeResponse() {

    companion object {
        fun of(moodSuggestion: MoodSuggestion): MoodSuggestionResponse {
            val response = MoodSuggestionResponse(
                suggestionId = moodSuggestion.id,
                name = moodSuggestion.name,
            )
            response.setBaseTime(moodSuggestion)
            return response
        }
    }

}
