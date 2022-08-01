package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomMood
import kr.mashup.ladder.domain.room.domain.RoomRole

data class RoomDetailInfoResponse(
    val roomId: Long,
    val description: String,
    val invitationKey: String,
    val role: RoomRole?,
    val participantsCount: Int,
    val moods: List<RoomMoodResponse>,
    val playlistId: Long? = null,
) : BaseTimeResponse() {

    companion object {
        fun of(room: Room, playlistId: Long?, memberId: Long, moods: List<RoomMood>): RoomDetailInfoResponse {
            val response = RoomDetailInfoResponse(
                roomId = room.id,
                description = room.description,
                invitationKey = room.invitationKey.invitationKey,
                role = room.getRole(memberId),
                participantsCount = room.participants.size,
                moods = moods.map { mood -> RoomMoodResponse.of(mood) },
                playlistId = playlistId ?: -1L,
            )
            response.setBaseTime(room)
            return response
        }
    }

}
