package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomRole

data class RoomInfoResponse(
    val roomId: Long,
    val description: String,
    val role: RoomRole?,
    val participantsCount: Int,
) : BaseTimeResponse() {

    companion object {
        fun from(room: Room, memberId: Long): RoomInfoResponse {
            val response = RoomInfoResponse(
                roomId = room.id,
                description = room.name,
                role = room.getRole(memberId),
                participantsCount = room.participants.size,
            )
            response.setBaseTime(room)
            return response
        }
    }

}
