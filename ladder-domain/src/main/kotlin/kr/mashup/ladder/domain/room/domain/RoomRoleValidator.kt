package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.common.exception.model.ForbiddenException
import kr.mashup.ladder.domain.room.exception.RoomMemberConflictException

object RoomRoleValidator {

    fun validateCreator(room: Room, memberId: Long) {
        if (!room.isCreator(memberId)) {
            throw ForbiddenException("멤버($memberId)는 방($room.id)의 방장이 아닙니다")
        }
    }

    fun validateParticipant(room: Room, memberId: Long) {
        if (!room.isParticipant(memberId)) {
            throw ForbiddenException("멤버(${memberId})는 해당 방(${room.id})에 참여하고 있지 않습니다")
        }
    }

    fun validateNotParticipant(room: Room, memberId: Long) {
        if (room.isParticipant(memberId)) {
            throw RoomMemberConflictException("이미 해당하는 방(${room.id})에 참여하고 있는 멤버(${memberId}) 입니다")
        }
    }

}
