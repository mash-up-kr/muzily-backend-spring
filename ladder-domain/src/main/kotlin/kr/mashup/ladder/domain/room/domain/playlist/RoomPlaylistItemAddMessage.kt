package kr.mashup.ladder.domain.room.domain.playlist

data class RoomPlaylistItemAddMessage(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemId: Long,
) {
    fun toEvent(): RoomPlaylistItemAddMessageReceiveEvent {
        return RoomPlaylistItemAddMessageReceiveEvent(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId
        )
    }
}
