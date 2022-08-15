package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItem

data class RoomPlaylistItemRequestResponse(
    val playlistItemId: Long,
    val playlistId: Long,
    val videoId: String,
    val title: String,
    val thumbnail: String,
    val duration: Int,
    val dominantColor: String?,
    val senderId: Long,
) {
    companion object {
        fun of(item: PlaylistItem, senderId: Long): RoomPlaylistItemRequestResponse {
            return RoomPlaylistItemRequestResponse(
                playlistItemId = item.id,
                playlistId = item.playlist.id,
                videoId = item.videoId,
                title = item.title,
                thumbnail = item.thumbnail,
                duration = item.duration,
                dominantColor = item.dominantColor,
                senderId = senderId
            )
        }
    }
}
