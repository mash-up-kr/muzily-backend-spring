package kr.mashup.ladder.domain.common.exception.model

import kr.mashup.ladder.domain.common.exception.ErrorCode

/**
 * UnAuthorizedException
 * 401 UnAuthorized
 */
data class UnAuthorizedException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.UNAUTHORIZED,
) : LadderBaseException(message, errorCode)
