package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.mood.domain.Mood
import kr.mashup.ladder.domain.room.domain.emoji.EmojiType

data class RoomMoodResponse(
    val name: String,
    val emojiType: EmojiType,
) {

    companion object {
        fun of(mood: Mood) = RoomMoodResponse(
            name = mood.name,
            emojiType = mood.emojiType
        )
    }

}
