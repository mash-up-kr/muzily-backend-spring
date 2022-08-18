package kr.mashup.ladder.domain.room.exception

import kr.mashup.ladder.domain.common.exception.ErrorCode
import kr.mashup.ladder.domain.common.exception.model.LadderBaseException

data class RoomConflictException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.ALREADY_ROOM_CREATOR_CONFLICT,
) : LadderBaseException(message, errorCode)
