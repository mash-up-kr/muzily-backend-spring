package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemRequestDeclineMessage

class RoomDeclinePlaylistItemRequestRequest(
    val playlistId: Long,
    val playlistItemId: Long,
) {
    fun toMessage(roomId: Long): RoomPlaylistItemRequestDeclineMessage {
        return RoomPlaylistItemRequestDeclineMessage(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId
        )
    }
}
