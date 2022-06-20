package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.LadderBaseException

data class RoomNotFoundException(
    override val message: String = "",
    override val errorCode: ErrorCode = ErrorCode.ROOM_NOT_FOUND,
) : LadderBaseException(message, errorCode)
