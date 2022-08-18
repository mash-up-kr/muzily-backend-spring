package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.emoji.EmojiType

data class RoomDetailInfoResponse(
    val roomId: Long,
    val name: String,
    val emojiType: EmojiType,
    val invitation: RoomInviteInfoResponse,
    val participantsCount: Int,
    val currentUser: RoomMyRoleResponse,
    val playlist: RoomPlaylistInfoResponse?,
) : BaseTimeResponse() {

    companion object {
        fun of(room: Room, playlistId: Long?, memberId: Long): RoomDetailInfoResponse {
            val response = RoomDetailInfoResponse(
                roomId = room.id,
                name = room.name,
                invitation = RoomInviteInfoResponse.of(room.invitationKey),
                participantsCount = room.participants.size,
                emojiType = room.emojiType,
                currentUser = RoomMyRoleResponse.of(room = room, memberId = memberId),
                playlist = playlistId?.let { RoomPlaylistInfoResponse.of(playlistId = it) }
            )
            response.setBaseTime(room)
            return response
        }
    }

}


data class RoomPlaylistInfoResponse(
    val playlistId: Long,
) {

    companion object {
        fun of(playlistId: Long) = RoomPlaylistInfoResponse(
            playlistId = playlistId
        )
    }

}
