package kr.mashup.ladder.room.dto.response

data class RoomPlaylistItemRemoveResponse(
    val playlistId: Long,
    val playlistItemIds: List<Long>,
)
