package kr.mashup.ladder.common.exception.model

import kr.mashup.ladder.common.exception.ErrorCode

abstract class LadderBaseException(
    override val message: String,
    open val errorCode: ErrorCode,
) : RuntimeException(message)
