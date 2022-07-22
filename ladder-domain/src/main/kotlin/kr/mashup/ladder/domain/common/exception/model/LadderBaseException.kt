package kr.mashup.ladder.domain.common.exception.model

import kr.mashup.ladder.domain.common.exception.ErrorCode

abstract class LadderBaseException(
    override val message: String,
    open val errorCode: ErrorCode,
) : RuntimeException(message)
