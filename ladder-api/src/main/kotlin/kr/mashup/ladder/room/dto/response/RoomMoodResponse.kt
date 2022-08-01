package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.room.domain.RoomMood

data class RoomMoodResponse(
    val name: String,
    val emoji: String,
) {

    companion object {
        fun of(roomMood: RoomMood): RoomMoodResponse {
            return RoomMoodResponse(
                name = roomMood.name,
                emoji = roomMood.emoji
            )
        }
    }

}
