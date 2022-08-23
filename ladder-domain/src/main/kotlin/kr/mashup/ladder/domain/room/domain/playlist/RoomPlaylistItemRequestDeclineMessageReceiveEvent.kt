package kr.mashup.ladder.domain.room.domain.playlist

class RoomPlaylistItemRequestDeclineMessageReceiveEvent(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemId: Long,
)
