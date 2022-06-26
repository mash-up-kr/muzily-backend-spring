package kr.mashup.ladder.domain.common.error.model

import kr.mashup.ladder.domain.common.error.ErrorCode

data class InvalidRequestException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.INVALID_REQUEST,
) : LadderBaseException(message, errorCode)
