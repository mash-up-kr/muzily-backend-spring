package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.emoji.EmojiType

data class RoomInfoResponse(
    val roomId: Long,
    val name: String,
    val emojiType: EmojiType,
    val participantsCount: Int,
    val playListItemsCount: Int,
    val currentUser: RoomMyRoleResponse,
) : BaseTimeResponse() {

    companion object {
        fun of(room: Room, memberId: Long, playListItemsCount: Int): RoomInfoResponse {
            val response = RoomInfoResponse(
                roomId = room.id,
                name = room.name,
                emojiType = room.emojiType,
                currentUser = RoomMyRoleResponse.of(room = room, memberId = memberId),
                participantsCount = room.participants.size,
                playListItemsCount = playListItemsCount,
            )
            response.setBaseTime(room)
            return response
        }
    }

}
