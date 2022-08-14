package kr.mashup.ladder.domain.mood.exception

import kr.mashup.ladder.domain.common.exception.ErrorCode
import kr.mashup.ladder.domain.common.exception.model.LadderBaseException

data class MoodRecommendNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.MOOD_RECOMMEND_NOT_FOUND,
) : LadderBaseException(message, errorCode)
