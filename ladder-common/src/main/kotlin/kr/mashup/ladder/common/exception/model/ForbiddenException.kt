package kr.mashup.ladder.common.exception.model

import kr.mashup.ladder.common.exception.ErrorCode

data class ForbiddenException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.FORBIDDEN,
) : LadderBaseException(message, errorCode)
