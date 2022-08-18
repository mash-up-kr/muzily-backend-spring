package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomRole
import kr.mashup.ladder.domain.room.domain.emoji.EmojiType

data class RoomDetailInfoResponse(
    val roomId: Long,
    val description: String,
    val invitationKey: String,
    val role: RoomRole?,
    val participantsCount: Int,
    val emojiType: EmojiType,
    val playlistId: Long? = null,
) : BaseTimeResponse() {

    companion object {
        fun of(room: Room, playlistId: Long?, memberId: Long): RoomDetailInfoResponse {
            val response = RoomDetailInfoResponse(
                roomId = room.id,
                description = room.name,
                invitationKey = room.invitationKey.invitationKey,
                role = room.getRole(memberId),
                participantsCount = room.participants.size,
                playlistId = playlistId ?: -1L,
                emojiType = room.emojiType,
            )
            response.setBaseTime(room)
            return response
        }
    }

}
