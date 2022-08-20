package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.playlist.domain.PlayStatus
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlayInformationUpdateMessage

data class RoomUpdatePlayInformationRequest(
    val playlistId: Long,
    val playlistItemId: Long,
    val playStatus: PlayStatus,
) {
    fun toMessage(roomId: Long): RoomPlayInformationUpdateMessage {
        return RoomPlayInformationUpdateMessage(
            roomId = roomId,
            playlistId = playlistId,
            playlistItemId = playlistItemId,
            playStatus = playStatus,
        )
    }
}
