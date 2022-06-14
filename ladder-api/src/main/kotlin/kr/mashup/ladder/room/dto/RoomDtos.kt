package kr.mashup.ladder.room.dto

data class RoomCreateRequest(
    val description: String,
)

data class RoomChatRequest(
    val chat: String,
)

data class RoomChatResponse(
    val chat: String,
)
