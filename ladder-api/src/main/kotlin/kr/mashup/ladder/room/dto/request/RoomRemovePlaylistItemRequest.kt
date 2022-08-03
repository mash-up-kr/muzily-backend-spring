package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemRemoveMessage

data class RoomRemovePlaylistItemRequest(
    val playlistId: Long,
    val playlistItemId: Long,
) {
    fun toMessage(roomId: Long): RoomPlaylistItemRemoveMessage {
        return RoomPlaylistItemRemoveMessage(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId
        )
    }
}
