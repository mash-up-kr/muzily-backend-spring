package kr.mashup.ladder.domain.room.exception

import kr.mashup.ladder.domain.common.exception.ErrorCode
import kr.mashup.ladder.domain.common.exception.model.LadderBaseException

data class RoomMemberConflictException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.ALREADY_EXISTS_MEMBER_IN_ROOM_CONFLICT,
) : LadderBaseException(message, errorCode)
