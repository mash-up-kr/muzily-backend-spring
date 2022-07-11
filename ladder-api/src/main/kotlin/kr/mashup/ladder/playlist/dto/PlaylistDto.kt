package kr.mashup.ladder.playlist.dto

import kr.mashup.ladder.domain.playlist.domain.Playlist
import kr.mashup.ladder.playlistitem.dto.PlaylistItemDto

data class PlaylistDto(
    val playlistId: Long,
    val roomId: Long,
    val playlistItems: List<PlaylistItemDto>,
) {
    companion object {
        fun of(playlist: Playlist): PlaylistDto {
            return PlaylistDto(
                playlistId = playlist.id,
                roomId = playlist.roomId,
                playlistItems = playlist.getAcceptedItems().map { PlaylistItemDto.of(it) }
            )
        }
    }
}
