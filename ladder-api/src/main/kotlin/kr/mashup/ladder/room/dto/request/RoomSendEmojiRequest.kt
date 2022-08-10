package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.emoji.EmojiType
import kr.mashup.ladder.domain.room.domain.emoji.RoomEmojiMessage

data class RoomSendEmojiRequest(
    val emojiType: EmojiType,
    val intensity: Int,
) {
    fun toMessage(roomId: Long): RoomEmojiMessage {
        return RoomEmojiMessage(
            roomId = roomId,
            emojiType = emojiType,
            intensity = intensity,
        )
    }
}
