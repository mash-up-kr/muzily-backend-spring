package kr.mashup.ladder.domain.room.domain.chat

class RoomChatMessageReceiveEvent(
    val roomId: Long,
    val senderId: Long,
    val chat: String,
)
