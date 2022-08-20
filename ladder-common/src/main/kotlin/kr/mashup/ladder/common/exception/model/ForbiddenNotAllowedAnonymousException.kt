package kr.mashup.ladder.common.exception.model

import kr.mashup.ladder.common.exception.ErrorCode

data class ForbiddenNotAllowedAnonymousException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.FORBIDDEN_NOT_ALLOWED_ANONYMOUS,
) : LadderBaseException(message, errorCode)
