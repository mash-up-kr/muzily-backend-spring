package kr.mashup.ladder.playlistitem.service

import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemRepository
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemStatus
import kr.mashup.ladder.playlist.service.PlaylistService
import kr.mashup.ladder.playlistitem.dto.PlaylistItemDto
import kr.mashup.ladder.room.service.RoomAuthorizeCheckServiceHelper.validateIsRoomCreator
import kr.mashup.ladder.room.service.RoomService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistItemService(
    private val playlistService: PlaylistService,
    private val roomService: RoomService,
    private val playlistItemRepository: PlaylistItemRepository,
) {
    @Transactional(readOnly = true)
    fun findPending(playlistId: Long, memberId: Long): List<PlaylistItemDto> {
        val playlist = playlistService.findPlaylistById(playlistId)
        val room = roomService.findRoomById(playlist.roomId)
        validateIsRoomCreator(room, memberId)
        val items = playlistItemRepository.findAllByPlaylistIdAndStatusIs(
            playlist.id,
            PlaylistItemStatus.PENDING)
        return items.map { PlaylistItemDto.of(it) }
    }
}
