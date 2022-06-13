package kr.mashup.ladder.room.dto

data class RoomCreateRequest(
    val description: String,
)

data class RoomChatMessage(
    val chat: String,
)
