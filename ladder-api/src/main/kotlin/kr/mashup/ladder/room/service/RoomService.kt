package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.playlist.domain.Playlist
import kr.mashup.ladder.domain.playlist.domain.PlaylistRepository
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.exception.CreatedRoomNotFoundException
import kr.mashup.ladder.domain.room.exception.RoomConflictException
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.room.dto.request.RoomCreateRequest
import kr.mashup.ladder.room.dto.request.RoomUpdateRequest
import kr.mashup.ladder.room.dto.response.CreatedRoomResponse
import kr.mashup.ladder.room.dto.response.RoomDetailInfoResponse
import kr.mashup.ladder.room.service.RoomServiceHelper.findRoomById
import kr.mashup.ladder.room.service.RoomServiceHelper.findRoomByIdFetchMember
import kr.mashup.ladder.room.service.RoomServiceHelper.validateIsCreator
import kr.mashup.ladder.room.service.RoomServiceHelper.validateIsParticipant
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val playlistRepository: PlaylistRepository,
) {

    @Transactional
    fun create(request: RoomCreateRequest, memberId: Long): RoomDetailInfoResponse {
        validateNotExistsCreatedRoomByMember(memberId = memberId)
        val room: Room = roomRepository.save(request.toEntity(memberId = memberId))

        val playlist = playlistRepository.save(Playlist(room.id))
        return RoomDetailInfoResponse.of(
            room = room,
            playlistId = playlist.id,
            memberId = memberId,
        )
    }

    private fun validateNotExistsCreatedRoomByMember(memberId: Long) {
        if (roomRepository.existsRoomByMemberId(memberId)) {
            throw RoomConflictException("멤버($memberId)은 이미 등록한 방이 있습니다 멤버 당 1개의 방만 생성할 수 있습니다")
        }
    }

    @Transactional
    fun update(roomId: Long, request: RoomUpdateRequest, memberId: Long): RoomDetailInfoResponse {
        val room = findRoomByIdFetchMember(roomRepository, roomId = roomId)
        validateIsCreator(roomRepository = roomRepository, roomId = roomId, memberId = memberId)

        room.update(name = request.name, emojiType = request.emojiType)

        return RoomDetailInfoResponse.of(
            room = room,
            playlistId = playlistRepository.findByRoomId(room.id)?.id ?: -1,
            memberId = memberId,
        )
    }

    @Transactional(readOnly = true)
    fun getMyCreatedRoom(memberId: Long): CreatedRoomResponse {
        val roomId = roomRepository.findRoomIdByCreatorId(creatorId = memberId)
            ?: throw CreatedRoomNotFoundException("멤버($memberId)가 생성한 방이 존재하지 않습니다")
        return CreatedRoomResponse.of(roomId = roomId)
    }

    @Transactional(readOnly = true)
    fun getRoom(roomId: Long, memberId: Long): RoomDetailInfoResponse {
        val room = findRoomById(roomRepository, roomId)
        validateIsParticipant(roomRepository = roomRepository, roomId = room.id, memberId = memberId)

        return RoomDetailInfoResponse.of(
            room = room,
            playlistId = playlistRepository.findByRoomId(room.id)?.id ?: -1,
            memberId = memberId,
        )
    }

    @Transactional
    fun deleteRoom(roomId: Long, memberId: Long) {
        val room = findRoomById(roomRepository, roomId)
        validateIsCreator(roomRepository = roomRepository, roomId = roomId, memberId = memberId)
        room.delete()
    }

}
