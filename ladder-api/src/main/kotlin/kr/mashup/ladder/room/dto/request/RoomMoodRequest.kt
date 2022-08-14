package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.mood.domain.Mood

data class RoomMoodRequest(
    val name: String,
    val emoji: String,
) {

    fun toEntity(roomId: Long): Mood {
        return Mood(
            roomId = roomId,
            name = name,
            emoji = emoji
        )
    }

}
