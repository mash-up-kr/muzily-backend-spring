package kr.mashup.ladder.domain.mood.event

data class MoodSuggestionReceivedEvent(
    val roomId: Long,
    val name: String,
)
