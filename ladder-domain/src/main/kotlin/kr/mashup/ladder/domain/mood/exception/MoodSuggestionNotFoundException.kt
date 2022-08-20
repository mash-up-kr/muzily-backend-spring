package kr.mashup.ladder.domain.mood.exception

import kr.mashup.ladder.common.exception.ErrorCode
import kr.mashup.ladder.common.exception.model.LadderBaseException

data class MoodSuggestionNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.MOOD_SUGGESTION_NOT_FOUND,
) : LadderBaseException(message, errorCode)
