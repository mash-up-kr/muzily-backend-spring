package kr.mashup.ladder.mood.dto.request

import kr.mashup.ladder.domain.mood.domain.MoodSuggestion
import kr.mashup.ladder.domain.mood.domain.MoodSuggestionMessage
import javax.validation.constraints.NotBlank

data class SuggestMoodRequest(
    @field:NotBlank(message = "요청할 분위기를 입력해주세요")
    val name: String = "",
) {

    fun toEntity(roomId: Long, memberId: Long) = MoodSuggestion.of(
        roomId = roomId,
        name = name,
        recommenderId = memberId,
    )

    fun toMessage(roomId: Long) = MoodSuggestionMessage(
        roomId = roomId,
        name = name
    )

}
