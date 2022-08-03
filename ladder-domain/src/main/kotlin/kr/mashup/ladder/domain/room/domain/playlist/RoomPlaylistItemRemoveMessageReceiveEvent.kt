package kr.mashup.ladder.domain.room.domain.playlist

class RoomPlaylistItemRemoveMessageReceiveEvent(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemId: Long,
)
