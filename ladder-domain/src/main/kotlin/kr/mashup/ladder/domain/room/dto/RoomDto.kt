package kr.mashup.ladder.domain.room.dto

import kr.mashup.ladder.domain.room.domain.Room
import java.time.LocalDateTime

data class RoomDto(
    val roomId: Long,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(room: Room): RoomDto {
            return RoomDto(
                roomId = room.roomId,
                description = room.description,
                createdAt = room.createdAt,
                updatedAt = room.updatedAt
            )
        }
    }
}
