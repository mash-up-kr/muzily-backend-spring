package kr.mashup.ladder.search.youtube.external.dto.error

import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.LadderBaseException

data class YoutubeVideoNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.YOUTUBE_VIDEO_NOT_FOUND,
) : LadderBaseException(message, errorCode)
