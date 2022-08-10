package kr.mashup.ladder.domain.room.domain.emoji

data class RoomEmojiMessage(
    val roomId: Long,
    val emojiType: EmojiType,
    val intensity: Int,
) {
    fun toEvent(): RoomEmojiMessageRecieveEvent {
        return RoomEmojiMessageRecieveEvent(
            roomId = roomId,
            emojiType = emojiType,
            intensity = intensity
        )
    }
}
