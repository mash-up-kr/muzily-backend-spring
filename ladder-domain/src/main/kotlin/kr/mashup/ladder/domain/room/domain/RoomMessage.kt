package kr.mashup.ladder.domain.room.domain

data class RoomMessage<T>(
    val type: RoomMessageType,
    val data: T
)
