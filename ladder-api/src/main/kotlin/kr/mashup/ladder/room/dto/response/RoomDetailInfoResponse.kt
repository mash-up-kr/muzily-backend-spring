package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.room.domain.Room

data class RoomDetailInfoResponse(
    val roomId: Long,
    val description: String,
    val invitationKey: String,
    val participantsCount: Long = 0L, // TODO: 참여자 카운트 조회
    val moods: List<String>,
) : BaseTimeResponse() {

    companion object {
        fun from(room: Room): RoomDetailInfoResponse {
            val response = RoomDetailInfoResponse(
                roomId = room.id,
                description = room.description,
                invitationKey = room.invitationKey.invitationKey,
                moods = room.moods.map { mood -> mood.name }
            )
            response.setBaseTime(room)
            return response
        }
    }

}
