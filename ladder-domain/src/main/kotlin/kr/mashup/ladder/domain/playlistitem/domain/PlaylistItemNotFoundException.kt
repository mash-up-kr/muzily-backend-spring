package kr.mashup.ladder.domain.playlistitem.domain

import kr.mashup.ladder.common.exception.ErrorCode
import kr.mashup.ladder.common.exception.model.LadderBaseException

data class PlaylistItemNotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.PLAYLIST_ITEM_NOT_FOUND,
) : LadderBaseException(message, errorCode)
