package kr.mashup.ladder.domain.room.exception

import kr.mashup.ladder.domain.common.exception.ErrorCode
import kr.mashup.ladder.domain.common.exception.model.LadderBaseException

data class CreatedRoomNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.CREATED_ROOM_NOT_FOUND,
) : LadderBaseException(message, errorCode)
