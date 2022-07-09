package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.Room
import javax.validation.constraints.NotBlank

data class RoomCreateRequest(
    @field:NotBlank
    val description: String,
) {

    fun toEntity(accountId: Long): Room {
        return Room.newInstance(
            description = description,
            memberId = accountId
        )
    }

}
