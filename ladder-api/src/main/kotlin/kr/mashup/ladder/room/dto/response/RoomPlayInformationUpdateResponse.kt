package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.playlist.domain.PlayStatus

data class RoomPlayInformationUpdateResponse(
    val playlistId: Long,
    val playlistItemId: Long,
    val playStatus: PlayStatus,
)
