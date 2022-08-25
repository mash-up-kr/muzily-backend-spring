package kr.mashup.ladder.domain.mood.domain

import kr.mashup.ladder.domain.mood.event.MoodSuggestionReceivedEvent

data class MoodSuggestionMessage(
    val roomId: Long,
    val name: String,
) {

    fun toEvent(): MoodSuggestionReceivedEvent {
        return MoodSuggestionReceivedEvent(
            roomId = roomId,
            name = name,
        )
    }

}
