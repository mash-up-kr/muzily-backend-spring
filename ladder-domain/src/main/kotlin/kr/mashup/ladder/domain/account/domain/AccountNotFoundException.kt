package kr.mashup.ladder.domain.account.domain

import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.LadderBaseException

data class AccountNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.ACCOUNT_NOT_FOUND,
) : LadderBaseException(message, errorCode)
