package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.room.domain.Room

data class RoomDetailInfoResponse(
    val roomId: Long,
    val description: String,
    val invitationKey: String,
) : BaseTimeResponse() {

    companion object {
        fun from(room: Room): RoomDetailInfoResponse {
            val response = RoomDetailInfoResponse(
                roomId = room.id,
                description = room.description,
                invitationKey = room.invitationKey.invitationKey,
            )
            response.setBaseTime(room)
            return response
        }
    }

}
