package kr.mashup.ladder.playlist.service

import kr.mashup.ladder.domain.playlist.domain.Playlist
import kr.mashup.ladder.domain.playlist.domain.PlaylistNotFoundException
import kr.mashup.ladder.domain.playlist.domain.PlaylistRepository
import kr.mashup.ladder.playlist.dto.PlaylistDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository,
) {
    fun getPlaylist(playlistId: Long): PlaylistDto {
        val playlist = findPlaylistById(playlistId)
        return PlaylistDto.of(playlist)
    }

    fun findPlaylistById(playlistId: Long): Playlist {
        return playlistRepository.findByIdOrNull(playlistId)
            ?: throw PlaylistNotFoundException("해당하는 재생목록(${playlistId})이 존재하지 않습니다")
    }
}
