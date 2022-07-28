package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.Room
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RoomCreateRequest(
    @field:NotBlank
    val description: String,
    @field:Size(max = 10)
    val moods: Set<RoomMoodRequest> = setOf(),
) {

    fun toEntity(memberId: Long): Room {
        return Room.newInstance(
            description = description,
            memberId = memberId
        )
    }

}

