package kr.mashup.ladder.domain.member.domain

import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.LadderBaseException

data class MemberNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.MEMBER_NOT_FOUND,
) : LadderBaseException(message, errorCode)
