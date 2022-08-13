package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.common.exception.ErrorCode.ROOM_IS_NOT_CREATOR_FORBIDDEN
import kr.mashup.ladder.domain.common.exception.model.ForbiddenException
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.exception.RoomNotFoundException
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository

object RoomServiceUtils {

    fun validateIsAuthor(roomRepository: RoomRepository, roomId: Long, memberId: Long) {
        if (!roomRepository.existsRoomByIdAndCreatorId(roomId = roomId, creatorId = memberId)) {
            throw ForbiddenException(
                "멤버($memberId)는 해당하는 방($roomId)에 대한 권한이 없습니다",
                ROOM_IS_NOT_CREATOR_FORBIDDEN
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
            ?: throw RoomNotFoundException("해당하는 초대장(${invitationKey})에 해당하는 방은 존재하지 않습니다")
    }

}
