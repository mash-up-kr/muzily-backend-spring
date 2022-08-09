package kr.mashup.ladder.playlist.dto

import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItem

data class PlaylistItemDto(
    val id: Long,
    val playlistId: Long,
    val videoId: String,
    val title: String,
    val thumbnail: String,
    val duration: Int,
    val dominantColor: String?,
) {
    companion object {
        fun of(item: PlaylistItem): PlaylistItemDto {
            return PlaylistItemDto(
                id = item.id,
                playlistId = item.playlist.id,
                videoId = item.videoId,
                title = item.title,
                thumbnail = item.thumbnail,
                duration = item.duration,
                dominantColor = item.dominantColor,
            )
        }
    }
}
