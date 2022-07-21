package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.playlist.domain.Playlist
import kr.mashup.ladder.domain.playlist.domain.PlaylistRepository
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomConflictException
import kr.mashup.ladder.domain.room.domain.RoomNotFoundException
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.room.dto.request.RoomCreateRequest
import kr.mashup.ladder.room.dto.request.RoomUpdateRequest
import kr.mashup.ladder.room.dto.response.RoomDetailInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val playlistRepository: PlaylistRepository,
) {

    @Transactional
    fun create(request: RoomCreateRequest, memberId: Long): RoomDetailInfoResponse {
        if (roomRepository.existsRoomByMemberId(memberId)) {
            throw RoomConflictException("멤버($memberId)은 이미 등록한 방이 있습니다 멤버 당 1개의 방만 생성할 수 있습니다")
        }
        val room = roomRepository.save(request.toEntity(memberId = memberId))
        val playlist = playlistRepository.save(Playlist(room.id))
        return RoomDetailInfoResponse.of(room = room, playlistId = playlist.id, memberId = memberId)
    }

    @Transactional
    fun update(roomId: Long, request: RoomUpdateRequest, memberId: Long): RoomDetailInfoResponse {
        val room = findRoomById(roomId)
        room.validateCreator(memberId)
        room.update(request.description)
        room.updateMoods(request.moods)
        val playlist = playlistRepository.findByRoomId(room.id)
        return RoomDetailInfoResponse.of(room = room, playlistId = playlist.id, memberId = memberId)
    }

    @Transactional(readOnly = true)
    fun getMyRooms(memberId: Long): List<RoomDetailInfoResponse> {
        val rooms: List<Room> = roomRepository.findRoomsByMemberId(memberId)
        val playlists = playlistRepository.findByRoomIdIn(rooms.map { it.id })
        return RoomDetailInfoResponse.of(rooms = rooms, playlists = playlists, memberId = memberId)
    }

    @Transactional(readOnly = true)
    fun getRoom(roomId: Long, memberId: Long): RoomDetailInfoResponse {
        val room = findRoomById(roomId)
        room.validateParticipant(memberId = memberId)
        val playlist = playlistRepository.findByRoomId(room.id)
        return RoomDetailInfoResponse.of(room, playlist.id, memberId)
    }

    @Transactional
    fun deleteRoom(roomId: Long, memberId: Long) {
        val room = findRoomById(roomId)
        room.validateCreator(memberId)
        room.delete()
    }

    fun findRoomById(roomId: Long): Room {
        return roomRepository.findRoomById(roomId)
            ?: throw RoomNotFoundException("해당하는 방(${roomId})이 존재하지 않습니다")
    }

}
