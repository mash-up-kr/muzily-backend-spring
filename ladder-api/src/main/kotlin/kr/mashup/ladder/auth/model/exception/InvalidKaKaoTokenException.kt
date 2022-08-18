package kr.mashup.ladder.auth.model.exception

import kr.mashup.ladder.domain.common.exception.ErrorCode
import kr.mashup.ladder.domain.common.exception.model.LadderBaseException

data class InvalidKaKaoTokenException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.INVALID_AUTH_CODE,
) : LadderBaseException(message, errorCode)
