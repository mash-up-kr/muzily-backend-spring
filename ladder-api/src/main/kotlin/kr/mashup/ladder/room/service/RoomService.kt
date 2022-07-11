package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.playlist.domain.Playlist
import kr.mashup.ladder.domain.playlist.domain.PlaylistRepository
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemRepository
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomConflictException
import kr.mashup.ladder.domain.room.domain.RoomMessage
import kr.mashup.ladder.domain.room.domain.RoomMessagePublisher
import kr.mashup.ladder.domain.room.domain.RoomMessageType
import kr.mashup.ladder.domain.room.domain.RoomNotFoundException
import kr.mashup.ladder.domain.room.domain.RoomTopic
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.room.dto.request.RoomCreateRequest
import kr.mashup.ladder.room.dto.request.RoomSendChatRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomUpdateRequest
import kr.mashup.ladder.room.dto.response.RoomDetailInfoResponse
import kr.mashup.ladder.room.dto.response.RoomInfoResponse
import kr.mashup.ladder.room.service.RoomAuthorizeCheckServiceHelper.validateIsRoomCreator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val roomMessagePublisher: RoomMessagePublisher,
    private val playlistRepository: PlaylistRepository,
    private val playlistItemRepository: PlaylistItemRepository
) {

    @Transactional
    fun create(request: RoomCreateRequest, memberId: Long): RoomDetailInfoResponse {
        validateNotExistsCreatedRoom(memberId)
        val room = roomRepository.save(request.toEntity(accountId = memberId))
        playlistRepository.save(Playlist(room.id))
        return RoomDetailInfoResponse.from(room)
    }

    private fun validateNotExistsCreatedRoom(accountId: Long) {
        if (roomRepository.existsRoomByMemberId(accountId)) {
            throw RoomConflictException("계정($accountId)은 이미 등록한 방이 있습니다")
        }
    }

    @Transactional
    fun update(roomId: Long, request: RoomUpdateRequest, memberId: Long): RoomDetailInfoResponse {
        val room = findRoomById(roomId)
        validateIsRoomCreator(room = room, memberId = memberId)
        room.update(request.description)
        room.updateMoods(request.moods)
        return RoomDetailInfoResponse.from(room)
    }

    @Transactional(readOnly = true)
    fun getMyRooms(memberId: Long): List<RoomDetailInfoResponse> {
        val rooms: List<Room> = roomRepository.findRoomsByMemberId(memberId)
        return rooms.map { room -> RoomDetailInfoResponse.from(room) }
    }

    @Transactional(readOnly = true)
    fun getRoom(roomId: Long): RoomDetailInfoResponse {
        val room = findRoomById(roomId)
        // TODO: 방에 참여중인 멤버인지 권한 체크 추가
        return RoomDetailInfoResponse.from(room)
    }

    @Transactional(readOnly = true)
    fun getByInvitationKey(invitationKey: String): RoomInfoResponse {
        val room = roomRepository.findRoomByInvitationKey(invitationKey)
            ?: throw RoomNotFoundException("해당하는 초대장(${invitationKey})에 해당하는 방은 존재하지 않습니다")
        return RoomInfoResponse.from(room)
    }

    @Transactional
    fun deleteRoom(roomId: Long, memberId: Long) {
        val room = findRoomById(roomId)
        validateIsRoomCreator(room = room, memberId = memberId)
        room.delete()
    }

    fun findRoomById(roomId: Long): Room {
        return roomRepository.findRoomById(roomId)
            ?: throw RoomNotFoundException("해당하는 방(${roomId})이 존재하지 않습니다")
    }

    fun sendChat(roomId: Long, request: RoomSendChatRequest) {
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.CHAT, request.toMessage(roomId))
        )
    }

    fun sendEmoji(roomId: Long, request: RoomSendEmojiRequest) {
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.EMOJI, request.toMessage(roomId))
        )
    }

    fun sendPlaylistItemRequest(roomId: Long, request: RoomSendPlaylistItemRequest) {
        val item = playlistItemRepository.save(request.toEntity())
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAYLIST_ITEM_REQUEST, request.toMessage(roomId, item.id))
        )
    }
}
