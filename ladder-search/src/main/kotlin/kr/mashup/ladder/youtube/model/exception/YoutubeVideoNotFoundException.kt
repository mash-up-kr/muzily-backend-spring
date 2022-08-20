package kr.mashup.ladder.youtube.model.exception

import kr.mashup.ladder.common.exception.ErrorCode
import kr.mashup.ladder.common.exception.model.LadderBaseException

data class YoutubeVideoNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.YOUTUBE_VIDEO_NOT_FOUND,
) : LadderBaseException(message, errorCode)
