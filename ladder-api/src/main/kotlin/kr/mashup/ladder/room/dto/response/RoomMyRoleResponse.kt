package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomRole

data class RoomMyRoleResponse(
    val role: RoomRole?,
) {

    companion object {
        fun of(room: Room, memberId: Long) = RoomMyRoleResponse(
            role = room.getRole(memberId),
        )
    }

}
