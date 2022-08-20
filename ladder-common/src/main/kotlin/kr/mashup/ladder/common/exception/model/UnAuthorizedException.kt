package kr.mashup.ladder.common.exception.model

import kr.mashup.ladder.common.exception.ErrorCode

/**
 * UnAuthorizedException
 * 401 UnAuthorized
 */
data class UnAuthorizedException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.UNAUTHORIZED,
) : LadderBaseException(message, errorCode)
