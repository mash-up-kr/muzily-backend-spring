package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.mood.domain.Mood

data class RoomMoodResponse(
    val name: String,
    val emoji: String,
) {

    companion object {
        fun of(mood: Mood): RoomMoodResponse {
            return RoomMoodResponse(
                name = mood.name,
                emoji = mood.emoji
            )
        }
    }

}
