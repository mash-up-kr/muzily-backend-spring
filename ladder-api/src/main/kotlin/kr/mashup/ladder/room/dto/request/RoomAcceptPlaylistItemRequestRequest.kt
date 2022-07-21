package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemAddMessage

class RoomAcceptPlaylistItemRequestRequest(
    val playlistId: Long,
    val playlistItemId: Long,
) {
    fun toMessage(roomId: Long): RoomPlaylistItemAddMessage {
        return RoomPlaylistItemAddMessage(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId
        )
    }
}
