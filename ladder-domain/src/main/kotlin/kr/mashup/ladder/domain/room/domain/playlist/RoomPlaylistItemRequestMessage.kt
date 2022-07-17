package kr.mashup.ladder.domain.room.domain.playlist

data class RoomPlaylistItemRequestMessage(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemId: Long,
) {
    fun toEvent(): RoomPlaylistItemRequestMessageReceiveEvent {
        return RoomPlaylistItemRequestMessageReceiveEvent(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId
        )
    }
}
