package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.room.domain.emoji.EmojiType

data class RoomMoodResponse(
    val moodDescription: String,
    val emojiType: EmojiType,
)
