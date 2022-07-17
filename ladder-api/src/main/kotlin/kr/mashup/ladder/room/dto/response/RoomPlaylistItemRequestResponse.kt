package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItem

data class RoomPlaylistItemRequestResponse(
    val playlistItemId: Long,
    val playlistId: Long,
    val videoId: String,
    val title: String,
    val thumbnail: String,
    val duration: Int,
) {
    companion object {
        fun of(playlistItem: PlaylistItem): RoomPlaylistItemRequestResponse {
            return RoomPlaylistItemRequestResponse(
                playlistItemId = playlistItem.id,
                playlistId = playlistItem.playlistId,
                videoId = playlistItem.videoId,
                title = playlistItem.title,
                thumbnail = playlistItem.thumbnail,
                duration = playlistItem.duration,
            )
        }
    }
}
