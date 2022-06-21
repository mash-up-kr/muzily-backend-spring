package kr.mashup.ladder.domain.common.error.model

import kr.mashup.ladder.domain.common.error.ErrorCode

abstract class LadderBaseException(
    override val message: String,
    open val errorCode: ErrorCode,
) : RuntimeException(message)
