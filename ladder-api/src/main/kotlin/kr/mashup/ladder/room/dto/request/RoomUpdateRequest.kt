package kr.mashup.ladder.room.dto.request

import javax.validation.constraints.NotBlank

data class RoomUpdateRequest(
    @field:NotBlank
    val description: String,

    val moods: Set<String> = setOf(),
)
