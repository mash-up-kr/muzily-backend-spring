package kr.mashup.ladder.room.service

import kr.mashup.ladder.common.exception.ErrorCode.IS_NOT_CREATOR_IN_ROOM_FORBIDDEN
import kr.mashup.ladder.common.exception.ErrorCode.IS_NOT_PARTICIPANT_IN_ROOM_FORBIDDEN
import kr.mashup.ladder.common.exception.model.ForbiddenException
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.exception.RoomNotFoundException
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository

object RoomServiceHelper {

    fun validateIsCreator(roomRepository: RoomRepository, roomId: Long, memberId: Long) {
        if (!roomRepository.existsRoomByIdAndCreatorId(roomId = roomId, creatorId = memberId)) {
            throw ForbiddenException(
                "멤버($memberId)는 방($roomId)의 방장이 아닙니다",
                IS_NOT_CREATOR_IN_ROOM_FORBIDDEN
            )
        }
    }

    fun validateIsParticipant(roomRepository: RoomRepository, roomId: Long, memberId: Long) {
        if (!roomRepository.existsRoomByIdAndMemberId(roomId = roomId, creatorId = memberId)) {
            throw ForbiddenException(
                "멤버($memberId)는 방($roomId)의 참가자가 아닙니다",
                IS_NOT_PARTICIPANT_IN_ROOM_FORBIDDEN
            )
        }
    }

    fun findRoomById(roomRepository: RoomRepository, roomId: Long): Room {
        return roomRepository.findRoomById(roomId)
            ?: throw RoomNotFoundException("해당하는 방(${roomId})이 존재하지 않습니다")
    }

    fun findRoomByIdFetchMember(roomRepository: RoomRepository, roomId: Long): Room {
        return roomRepository.findRoomByIdWithFetchMember(roomId)
            ?: throw RoomNotFoundException("해당하는 방(${roomId})이 존재하지 않습니다")
    }

    fun findRoomByInvitationKey(roomRepository: RoomRepository, invitationKey: String): Room {
        return roomRepository.findRoomByInvitationKey(invitationKey)
            ?: throw RoomNotFoundException("해당하는 초대장(${invitationKey})의 방은 존재하지 않습니다")
    }

}
