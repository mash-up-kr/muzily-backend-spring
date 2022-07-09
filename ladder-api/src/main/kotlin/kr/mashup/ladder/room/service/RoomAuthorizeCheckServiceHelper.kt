package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.common.error.model.ForbiddenException
import kr.mashup.ladder.domain.room.domain.Room

object RoomAuthorizeCheckServiceHelper {

    fun validateIsRoomCreator(room: Room, memberId: Long) {
        if (!room.isCreator(memberId)) {
            throw ForbiddenException("멤버($memberId)는 방($room.id)의 방장이 아닙니다")
        }
    }

}
