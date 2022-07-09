package kr.mashup.ladder.domain.common.error.model

import kr.mashup.ladder.domain.common.error.ErrorCode

data class ForbiddenException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.FORBIDDEN,
) : LadderBaseException(message, errorCode)
