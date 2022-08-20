package kr.mashup.ladder.domain.room.domain.playlist

import kr.mashup.ladder.domain.playlist.domain.PlayStatus

data class RoomPlayInformationUpdateMessage(
    val roomId: Long,
    val playlistId: Long,
    val playlistItemId: Long,
    val playStatus: PlayStatus,
) {
    fun toEvent(): RoomPlayInformationUpdateMessageReceiveEvent {
        return RoomPlayInformationUpdateMessageReceiveEvent(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId,
            playStatus = playStatus
        )
    }
}
