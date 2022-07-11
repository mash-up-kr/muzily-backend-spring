package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItem
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemRequestMessage

data class RoomSendPlaylistItemRequest(
    val playlistId: Long,
    val videoId: String,
    val title: String,
    val thumbnail: String,
    val duration: Int,
) {
    fun toEntity(): PlaylistItem {
        return PlaylistItem(
            playlistId = playlistId,
            videoId = videoId,
            title = title,
            thumbnail = thumbnail,
            duration = duration
        )
    }

    fun toMessage(roomId: Long, playlistItemId: Long): RoomPlaylistItemRequestMessage {
        return RoomPlaylistItemRequestMessage(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId
        )
    }
}
