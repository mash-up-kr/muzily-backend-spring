package kr.mashup.ladder.playlistitem.dto

import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItem

data class PlaylistItemDto(
    val playlistItemId: Long,
    val playlistId: Long,
    val videoId: String,
    val title: String,
    val thumbnail: String,
    val duration: Int,
) {
    companion object {
        fun of(playlistItem: PlaylistItem): PlaylistItemDto {
            return PlaylistItemDto(
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
