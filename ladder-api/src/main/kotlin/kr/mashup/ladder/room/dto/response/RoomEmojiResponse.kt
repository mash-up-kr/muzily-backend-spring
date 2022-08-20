package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.room.domain.emoji.EmojiType

data class RoomEmojiResponse(
    val emojiType: EmojiType,
    val intensity: Int,
    val messageText: String,
    val senderId: Long,
)
