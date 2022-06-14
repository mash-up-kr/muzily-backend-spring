package kr.mashup.ladder.room.dto

data class RoomCreateRequest(
    val description: String,
)

data class RoomChatPayload(
    val chat: String,
)
