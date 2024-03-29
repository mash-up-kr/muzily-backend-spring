package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.chat.RoomChatMessage

data class RoomSendChatRequest(
    val chat: String,
) {
    fun toMessage(roomId: Long, senderId: Long): RoomChatMessage {
        return RoomChatMessage(roomId, senderId, chat)
    }
}
