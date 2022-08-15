package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemChangeOrderMessage

class RoomChangeOrderOfPlaylistItemRequest(
    val playlistId: Long,
    val playlistItemId: Long,
    val prevPlaylistItemIdToMove: Long?,
) {
    fun toMessage(roomId: Long, order: List<Long>): RoomPlaylistItemChangeOrderMessage {
        return RoomPlaylistItemChangeOrderMessage(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId,
            order = order
        )
    }
}
