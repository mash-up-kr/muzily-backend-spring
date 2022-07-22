package kr.mashup.ladder.domain.room.exception

import kr.mashup.ladder.domain.common.exception.ErrorCode
import kr.mashup.ladder.domain.common.exception.model.LadderBaseException

data class RoomMemberConflictException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.ROOM_MEMBER_CONFLICT,
) : LadderBaseException(message, errorCode)
