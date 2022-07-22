package kr.mashup.ladder.domain.common.exception.model

import kr.mashup.ladder.domain.common.exception.ErrorCode

/**
 * UnknownErrorException
 * 500 InternalServer
 */
data class UnknownErrorException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.UNKNOWN_ERROR,
) : LadderBaseException(message, errorCode)
