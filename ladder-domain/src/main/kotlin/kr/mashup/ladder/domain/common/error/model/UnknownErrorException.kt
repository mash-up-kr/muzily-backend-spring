package kr.mashup.ladder.domain.common.error.model

import kr.mashup.ladder.domain.common.error.ErrorCode

/**
 * UnknownErrorException
 * 500 InternalServer
 */
data class UnknownErrorException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.UNKNOWN_ERROR,
) : LadderBaseException(message, errorCode)
