package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItem
import java.time.LocalDateTime

data class RoomPlaylistItemAddResponse(
    val playlistItemId: Long,
    val playlistId: Long,
    val videoId: String,
    val title: String,
    val thumbnail: String,
    val duration: Int,
    val dominantColor: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun of(item: PlaylistItem): RoomPlaylistItemAddResponse {
            return RoomPlaylistItemAddResponse(
                playlistItemId = item.id,
                playlistId = item.playlist.id,
                videoId = item.videoId,
                title = item.title,
                thumbnail = item.thumbnail,
                duration = item.duration,
                dominantColor = item.dominantColor,
                createdAt = item.createdAt,
                updatedAt = item.updatedAt
            )
        }
    }
}
