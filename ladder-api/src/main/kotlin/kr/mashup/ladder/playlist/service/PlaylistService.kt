package kr.mashup.ladder.playlist.service

import kr.mashup.ladder.domain.playlist.domain.Playlist
import kr.mashup.ladder.domain.playlist.domain.PlaylistNotFoundException
import kr.mashup.ladder.domain.playlist.domain.PlaylistRepository
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemRepository
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemStatus
import kr.mashup.ladder.playlist.dto.PlaylistDto
import kr.mashup.ladder.playlist.dto.PlaylistItemDto
import kr.mashup.ladder.room.service.RoomAuthorizeCheckServiceHelper
import kr.mashup.ladder.room.service.RoomService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository,
    private val playlistItemRepository: PlaylistItemRepository,
    private val roomService: RoomService,
) {
    @Transactional(readOnly = true)
    fun getPlaylist(playlistId: Long): PlaylistDto {
        val playlist = findPlaylistById(playlistId)
        return PlaylistDto.of(playlist)
    }

    @Transactional(readOnly = true)
    fun findPendingItems(playlistId: Long, memberId: Long): List<PlaylistItemDto> {
        val playlist = findPlaylistById(playlistId)
        val room = roomService.findRoomById(playlist.roomId)
        RoomAuthorizeCheckServiceHelper.validateIsRoomCreator(room, memberId)
        val items = playlistItemRepository.findAllByPlaylistIdAndStatusIs(
            playlist.id,
            PlaylistItemStatus.PENDING)
        return items.map { PlaylistItemDto.of(it) }
    }

    private fun findPlaylistById(playlistId: Long): Playlist {
        return playlistRepository.findByIdOrNull(playlistId)
            ?: throw PlaylistNotFoundException("해당하는 재생목록(${playlistId})이 존재하지 않습니다")
    }
}
