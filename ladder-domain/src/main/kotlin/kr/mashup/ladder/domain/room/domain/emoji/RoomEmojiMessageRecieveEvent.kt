package kr.mashup.ladder.domain.room.domain.emoji

class RoomEmojiMessageRecieveEvent(
    val roomId: Long,
    val senderId: Long,
    val emojiType: EmojiType,
    val intensity: Int,
    val messageText: String,
)
