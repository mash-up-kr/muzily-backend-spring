package kr.mashup.ladder.playlist.service

import kr.mashup.ladder.domain.playlist.domain.PlaylistNotFoundException
import kr.mashup.ladder.domain.playlist.domain.PlaylistRepository
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemNotFoundException
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemRepository
import kr.mashup.ladder.domain.room.exception.RoomNotFoundException
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.playlist.dto.PlaylistDto
import kr.mashup.ladder.playlist.dto.PlaylistItemDto
import kr.mashup.ladder.room.dto.request.RoomAcceptPlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomAddPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomChangeOrderOfPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomRemovePlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest
import kr.mashup.ladder.room.service.RoomServiceHelper.validateIsCreator
import kr.mashup.ladder.room.service.RoomServiceHelper.validateIsParticipant
import kr.mashup.ladder.room.dto.request.RoomUpdatePlayInformationRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository,
    private val playlistItemRepository: PlaylistItemRepository,
    private val roomRepository: RoomRepository,
) {
    @Transactional(readOnly = true)
    fun findById(playlistId: Long, memberId: Long): PlaylistDto {
        val playlist = playlistRepository.findByIdOrNull(playlistId) ?: throw PlaylistNotFoundException("$playlistId")
        validateIsParticipant(
            roomRepository = roomRepository,
            roomId = playlist.roomId,
            memberId = memberId
        )
        return PlaylistDto.of(playlist)
    }

    @Transactional(readOnly = true)
    fun findPendingItems(playlistId: Long, memberId: Long): List<PlaylistItemDto> {
        val playlist = playlistRepository.findByIdOrNull(playlistId) ?: throw PlaylistNotFoundException("$playlistId")
        validateIsCreator(
            roomRepository = roomRepository,
            roomId = playlist.roomId,
            memberId = memberId
        )
        val pendingItems = playlist.getPendingItems()
        return pendingItems.map { PlaylistItemDto.of(it) }
    }

    @Transactional
    fun addItemRequest(request: RoomSendPlaylistItemRequestRequest, memberId: Long): PlaylistItemDto {
        val playlist = playlistRepository.findByIdOrNull(request.playlistId)
            ?: throw PlaylistNotFoundException("${request.playlistId}")
        validateIsParticipant(
            roomRepository = roomRepository,
            roomId = playlist.roomId,
            memberId = memberId
        )
        val item = playlistItemRepository.save(request.toEntity(playlist))
        return PlaylistItemDto.of(item)
    }

    @Transactional
    fun acceptItemRequest(memberId: Long, request: RoomAcceptPlaylistItemRequestRequest): PlaylistItemDto {
        val playlist = playlistRepository.findByIdOrNull(request.playlistId)
            ?: throw PlaylistNotFoundException("${request.playlistId}")
        validateIsCreator(
            roomRepository = roomRepository,
            roomId = playlist.roomId,
            memberId = memberId
        )
        val item = playlistItemRepository.findByIdOrNull(request.playlistItemId)
            ?: throw PlaylistItemNotFoundException("${request.playlistItemId}")
        item.accept()
        playlist.addToOrder(item)
        return PlaylistItemDto.of(item)
    }

    @Transactional
    fun addItem(memberId: Long, request: RoomAddPlaylistItemRequest): PlaylistItemDto {
        val playlist = playlistRepository.findByIdOrNull(request.playlistId)
            ?: throw PlaylistNotFoundException("${request.playlistId}")
        validateIsCreator(
            roomRepository = roomRepository,
            roomId = playlist.roomId,
            memberId = memberId
        )
        val item = playlistItemRepository.save(request.toEntity(playlist))
        playlist.addToOrder(item)
        return PlaylistItemDto.of(item)
    }

    @Transactional
    fun removeItem(memberId: Long, request: RoomRemovePlaylistItemRequest) {
        val playlist = playlistRepository.findByIdOrNull(request.playlistId)
            ?: throw PlaylistNotFoundException("${request.playlistId}")
        validateIsCreator(
            roomRepository = roomRepository,
            roomId = playlist.roomId,
            memberId = memberId
        )
        playlistItemRepository.deleteById(request.playlistItemId)
        playlist.removeFromOrder(request.playlistItemId)
    }

    @Transactional
    fun changeOrder(memberId: Long, request: RoomChangeOrderOfPlaylistItemRequest): List<Long> {
        val playlist = playlistRepository.findByIdOrNull(request.playlistId)
            ?: throw PlaylistNotFoundException("${request.playlistId}")
        validateIsCreator(
            roomRepository = roomRepository,
            roomId = playlist.roomId,
            memberId = memberId
        )
        playlist.changeOrder(request.playlistItemId, request.prevPlaylistItemIdToMove)
        return playlist.order
    }

    @Transactional
    fun updatePlayInformation(memberId: Long, request: RoomUpdatePlayInformationRequest) {
        val playlist = playlistRepository.findByIdOrNull(request.playlistId)
            ?: throw PlaylistNotFoundException("${request.playlistId}")
        validateIsCreator(
            roomRepository = roomRepository,
            roomId = playlist.roomId,
            memberId = memberId
        )
        playlist.updatePlayInformation(request.playlistItemId, request.playStatus)
    }
}
