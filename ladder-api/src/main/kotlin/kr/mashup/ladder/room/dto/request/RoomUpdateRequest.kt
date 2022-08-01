package kr.mashup.ladder.room.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RoomUpdateRequest(
    @field:NotBlank
    val description: String,

    @field:Size(max = 10)
    val moods: Set<RoomMoodRequest> = setOf(),
)
