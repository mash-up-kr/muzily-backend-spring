package kr.mashup.ladder.domain.common.error.model

import kr.mashup.ladder.domain.common.error.ErrorCode

/**
 * UnAuthorizedException
 * 401 UnAuthorized
 */
data class UnAuthorizedException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.UNAUTHORIZED,
) : LadderBaseException(message, errorCode)
