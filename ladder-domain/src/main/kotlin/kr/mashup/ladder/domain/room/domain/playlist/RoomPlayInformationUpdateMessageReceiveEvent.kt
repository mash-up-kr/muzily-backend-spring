package kr.mashup.ladder.domain.room.domain.playlist

import kr.mashup.ladder.domain.playlist.domain.PlayStatus

data class RoomPlayInformationUpdateMessageReceiveEvent(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemId: Long,
    val playStatus: PlayStatus,
)
