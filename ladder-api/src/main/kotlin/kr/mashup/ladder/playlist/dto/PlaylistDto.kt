package kr.mashup.ladder.playlist.dto

import kr.mashup.ladder.domain.playlist.domain.PlayInformation
import kr.mashup.ladder.domain.playlist.domain.Playlist

data class PlaylistDto(
    val playlistId: Long,
    val roomId: Long,
    val playlistItems: List<PlaylistItemDto>,
    val order: List<Long>,
    val playInformation: PlayInformation,
) {
    companion object {
        fun of(playlist: Playlist): PlaylistDto {
            return PlaylistDto(
                playlistId = playlist.id,
                roomId = playlist.roomId,
                playlistItems = playlist.getAcceptedItems().map { PlaylistItemDto.of(it) },
                order = playlist.order,
                playInformation = playlist.playInformation,
            )
        }
    }
}
