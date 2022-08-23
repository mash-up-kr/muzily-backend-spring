package kr.mashup.ladder.domain.room.domain.playlist

data class RoomPlaylistItemRequestDeclineMessage(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemId: Long,
) {
    fun toEvent(): RoomPlaylistItemRequestDeclineMessageReceiveEvent {
        return RoomPlaylistItemRequestDeclineMessageReceiveEvent(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId
        )
    }
}
