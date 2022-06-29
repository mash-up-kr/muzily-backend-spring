package kr.mashup.ladder.domain.common.error.model

import kr.mashup.ladder.domain.common.error.ErrorCode

data class InvalidThirdPartyTokenException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.AUTH_TOKEN_INVALID,
) : LadderBaseException(message, errorCode)
