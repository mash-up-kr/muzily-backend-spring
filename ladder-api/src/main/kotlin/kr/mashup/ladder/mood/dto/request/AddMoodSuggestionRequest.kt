package kr.mashup.ladder.mood.dto.request

import kr.mashup.ladder.domain.mood.domain.MoodSuggestion
import javax.validation.Valid
import javax.validation.constraints.Size

data class AddMoodSuggestionRequest(
    @field:Valid
    val names: Set<String>,
) {

    fun toEntity(roomId: Long, memberId: Long): List<MoodSuggestion> {
        return names.map { name ->
            MoodSuggestion.of(
                roomId = roomId,
                recommenderId = memberId,
                name = name
            )
        }
    }

}
