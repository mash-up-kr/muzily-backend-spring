package kr.mashup.ladder.domain.room.domain.playlist

class RoomPlaylistItemAddMessageReceiveEvent(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemId: Long,
)
