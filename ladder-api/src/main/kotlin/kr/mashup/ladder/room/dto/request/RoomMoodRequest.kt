package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.RoomMood

data class RoomMoodRequest(
    val name: String,
    val emoji: String,
) {

    fun toEntity(roomId: Long): RoomMood {
        return RoomMood(
            roomId = roomId,
            name = name,
            emoji = emoji
        )
    }

}
