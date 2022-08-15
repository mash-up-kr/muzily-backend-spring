package kr.mashup.ladder.domain.room.domain.playlist

data class RoomPlaylistItemChangeOrderMessage(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemId: Long,
    val order: List<Long>,
) {
    fun toEvent(): RoomPlaylistItemChangeOrderReceiveEvent {
        return RoomPlaylistItemChangeOrderReceiveEvent(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId,
            order = order,
        )
    }
}
