package kr.mashup.ladder.domain.room.dto

import kr.mashup.ladder.domain.room.domain.Room
import java.time.LocalDateTime

data class RoomDto(
    val id: Long,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(room: Room): RoomDto {
            return RoomDto(
                id = room.id,
                description = room.description,
                createdAt = room.createdAt,
                updatedAt = room.updatedAt
            )
        }
    }
}
