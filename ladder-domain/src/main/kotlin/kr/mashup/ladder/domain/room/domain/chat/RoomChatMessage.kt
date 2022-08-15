package kr.mashup.ladder.domain.room.domain.chat

data class RoomChatMessage(
    val roomId: Long,
    val senderId: Long,
    val chat: String,
) {
    fun toEvent(): RoomChatMessageReceiveEvent {
        return RoomChatMessageReceiveEvent(
            roomId = roomId,
            senderId = senderId,
            chat = chat
        )
    }
}
