package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.room.domain.Room

data class RoomInfoResponse(
    val roomId: Long,
    val description: String,
    val participantsCount: Long = 0L, // TODO: 참여자 카운트 조회
) : BaseTimeResponse() {

    companion object {
        fun from(room: Room): RoomInfoResponse {
            val response = RoomInfoResponse(
                roomId = room.id,
                description = room.description,
            )
            response.setBaseTime(room)
            return response
        }
    }

}
