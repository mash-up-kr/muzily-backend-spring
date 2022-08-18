package kr.mashup.ladder.mood.dto.request

import kr.mashup.ladder.domain.mood.domain.MoodSuggestion
import javax.validation.Valid
import javax.validation.constraints.Size

data class AddMoodSuggestionRequest(
    @field:Valid
    @field:Size(max = 10, message = "방 분위기 추천은 최대 10개씩만 가능합니다")
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
