package kr.mashup.ladder.domain.room.domain.playlist

class RoomPlaylistItemChangeOrderReceiveEvent(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemId: Long,
    val order: List<Long>,
)
