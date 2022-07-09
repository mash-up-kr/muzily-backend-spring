package kr.mashup.ladder.room.service

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
import kr.mashup.ladder.room.dto.request.RoomUpdateRequest
import kr.mashup.ladder.room.dto.response.RoomDetailInfoResponse
import kr.mashup.ladder.room.dto.response.RoomInfoResponse
import kr.mashup.ladder.room.service.RoomAuthorizeCheckServiceHelper.validateIsRoomCreator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val roomMessagePublisher: RoomMessagePublisher,
) {

    @Transactional
    fun create(request: RoomCreateRequest, memberId: Long): RoomDetailInfoResponse {
        validateNotExistsCreatedRoom(memberId)
        val room = roomRepository.save(request.toEntity(accountId = memberId))
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
        return RoomDetailInfoResponse.from(room)
    }

    @Transactional(readOnly = true)
    fun getMyRooms(memberId: Long): RoomDetailInfoResponse {
        val room = roomRepository.findRoomByMemberId(memberId)
            ?: throw RoomNotFoundException("멤버($memberId)가 생성한 방이 없습니다")
        return RoomDetailInfoResponse.from(room)
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

    private fun findRoomById(roomId: Long): Room {
        return roomRepository.findByIdOrNull(roomId)
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
}
