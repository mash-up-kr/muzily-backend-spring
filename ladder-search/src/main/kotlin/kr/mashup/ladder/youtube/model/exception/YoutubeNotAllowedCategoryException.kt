package kr.mashup.ladder.youtube.model.exception

import kr.mashup.ladder.domain.common.exception.ErrorCode
import kr.mashup.ladder.domain.common.exception.model.LadderBaseException

data class YoutubeNotAllowedCategoryException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.YOUTUBE_VIDEO_NOT_ALLOWED_CATEGORY,
) : LadderBaseException(message, errorCode)
