package kr.mashup.ladder.domain.common.error.model

import kr.mashup.ladder.domain.common.error.ErrorCode

data class NeedAccountForbiddenException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.FORBIDDEN_NEED_ACCOUNT,
) : LadderBaseException(message, errorCode)
