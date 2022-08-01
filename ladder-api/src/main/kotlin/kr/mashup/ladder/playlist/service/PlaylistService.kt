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
import kr.mashup.ladder.room.dto.request.RoomRemovePlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest
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
    fun findById(playlistId: Long): PlaylistDto {
        val playlist = playlistRepository.findByIdOrNull(playlistId) ?: throw PlaylistNotFoundException("$playlistId")
        return PlaylistDto.of(playlist)
    }

    @Transactional(readOnly = true)
    fun findPendingItems(playlistId: Long, memberId: Long): List<PlaylistItemDto> {
        val playlist = playlistRepository.findByIdOrNull(playlistId) ?: throw PlaylistNotFoundException("$playlistId")
        val room = roomRepository.findByIdOrNull(playlist.roomId) ?: throw RoomNotFoundException("${playlist.roomId}")
        room.validateCreator(memberId)
        val pendingItems = playlist.getPendingItems()
        return pendingItems.map { PlaylistItemDto.of(it) }
    }

    @Transactional
    fun addItemRequest(request: RoomSendPlaylistItemRequestRequest): PlaylistItemDto {
        val playlist = playlistRepository.findByIdOrNull(request.playlistId)
            ?: throw PlaylistNotFoundException("${request.playlistId}")
        val item = playlistItemRepository.save(request.toEntity(playlist))
        return PlaylistItemDto.of(item)
    }

    @Transactional
    fun acceptItemRequest(memberId: Long, request: RoomAcceptPlaylistItemRequestRequest): PlaylistItemDto {
        val playlist = playlistRepository.findByIdOrNull(request.playlistId)
            ?: throw PlaylistNotFoundException("${request.playlistId}")
        val room = roomRepository.findByIdOrNull(playlist.roomId)
            ?: throw RoomNotFoundException("${playlist.roomId}")
        room.validateCreator(memberId)
        val item = playlistItemRepository.findByIdOrNull(request.playlistItemId)
            ?: throw PlaylistItemNotFoundException("${request.playlistItemId}")
        item.accept()
        return PlaylistItemDto.of(item)
    }

    @Transactional
    fun addItem(memberId: Long, request: RoomAddPlaylistItemRequest): PlaylistItemDto {
        val playlist = playlistRepository.findByIdOrNull(request.playlistId)
            ?: throw PlaylistNotFoundException("${request.playlistId}")
        val room = roomRepository.findByIdOrNull(playlist.roomId)
            ?: throw RoomNotFoundException("${playlist.roomId}")
        room.validateCreator(memberId)
        val item = playlistItemRepository.save(request.toEntity(playlist))
        return PlaylistItemDto.of(item)
    }

    @Transactional
    fun removeItem(memberId: Long, request: RoomRemovePlaylistItemRequest) {
        val playlist = playlistRepository.findByIdOrNull(request.playlistId)
            ?: throw PlaylistNotFoundException("${request.playlistId}")
        val room = roomRepository.findByIdOrNull(playlist.roomId)
            ?: throw RoomNotFoundException("${playlist.roomId}")
        room.validateCreator(memberId)
        playlistItemRepository.deleteById(request.playlistItemId)
    }
}
