package kr.mashup.ladder.domain.room.domain.emoji

data class RoomEmojiMessage(
    val roomId: Long,
    val senderId: Long,
    val emojiType: EmojiType,
    val intensity: Int,
) {
    fun toEvent(): RoomEmojiMessageRecieveEvent {
        return RoomEmojiMessageRecieveEvent(
            roomId = roomId,
            senderId = senderId,
            emojiType = emojiType,
            intensity = intensity
        )
    }
}
