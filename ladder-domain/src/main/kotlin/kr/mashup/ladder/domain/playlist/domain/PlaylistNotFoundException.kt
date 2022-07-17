package kr.mashup.ladder.domain.playlist.domain

import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.LadderBaseException

data class PlaylistNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.PLAYLIST_NOT_FOUND,
) : LadderBaseException(message, errorCode)
