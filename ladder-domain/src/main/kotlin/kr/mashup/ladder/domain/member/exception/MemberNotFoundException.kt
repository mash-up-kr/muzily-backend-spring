package kr.mashup.ladder.domain.member.exception

import kr.mashup.ladder.domain.common.exception.ErrorCode
import kr.mashup.ladder.domain.common.exception.model.LadderBaseException

data class MemberNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.MEMBER_NOT_FOUND,
) : LadderBaseException(message, errorCode)
