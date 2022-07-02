package kr.mashup.ladder.auth.external.dto.error

import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.LadderBaseException

data class InvalidKaKaoTokenException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.KAKAO_INVALID_AUTHORIZATION_CODE,
) : LadderBaseException(message, errorCode)
