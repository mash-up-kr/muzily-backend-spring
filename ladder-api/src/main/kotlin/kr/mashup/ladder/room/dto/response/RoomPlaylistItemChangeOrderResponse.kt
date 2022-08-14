package kr.mashup.ladder.room.dto.response

data class RoomPlaylistItemChangeOrderResponse(
    val playlistId: Long,
    val playlistItemId: Long,
    val order: List<Long>,
)
