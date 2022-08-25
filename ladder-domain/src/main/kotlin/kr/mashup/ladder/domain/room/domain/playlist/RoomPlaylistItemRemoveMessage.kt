package kr.mashup.ladder.domain.room.domain.playlist

data class RoomPlaylistItemRemoveMessage(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemIds: List<Long>,
) {
    fun toEvent(): RoomPlaylistItemRemoveMessageReceiveEvent {
        return RoomPlaylistItemRemoveMessageReceiveEvent(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemIds = playlistItemIds
        )
    }
}
