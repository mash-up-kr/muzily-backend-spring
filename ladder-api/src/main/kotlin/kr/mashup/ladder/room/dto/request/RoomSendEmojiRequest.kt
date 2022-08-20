package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.emoji.EmojiType
import kr.mashup.ladder.domain.room.domain.emoji.RoomEmojiMessage

data class RoomSendEmojiRequest(
    val emojiType: EmojiType,
    val intensity: Int,
    val messageText: String,
) {
    fun toMessage(roomId: Long, senderId: Long): RoomEmojiMessage {
        return RoomEmojiMessage(
            roomId = roomId,
            senderId = senderId,
            emojiType = emojiType,
            intensity = intensity,
            messageText = messageText,
        )
    }
}
