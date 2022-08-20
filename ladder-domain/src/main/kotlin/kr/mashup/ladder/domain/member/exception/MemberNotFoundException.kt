package kr.mashup.ladder.domain.member.exception

import kr.mashup.ladder.common.exception.ErrorCode
import kr.mashup.ladder.common.exception.model.LadderBaseException

data class MemberNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.MEMBER_NOT_FOUND,
) : LadderBaseException(message, errorCode)
