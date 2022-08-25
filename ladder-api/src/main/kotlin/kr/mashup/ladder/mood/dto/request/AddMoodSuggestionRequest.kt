package kr.mashup.ladder.mood.dto.request

import kr.mashup.ladder.domain.mood.domain.MoodSuggestion
import javax.validation.Valid
import javax.validation.constraints.Size

data class AddMoodSuggestionRequest(
    @field:Valid
    @field:Size(min = 1, max = 10, message = "분위기 추천은 1개 ~ 10개 사이로 가능합니다")
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
