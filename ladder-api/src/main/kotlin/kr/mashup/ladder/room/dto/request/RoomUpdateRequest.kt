package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.room.domain.emoji.EmojiType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class RoomUpdateRequest(
    @field:NotBlank(message = "방의 이름을 입력해주세요")
    val name: String = "",

    @field:NotBlank(message = "방 분위기를 입력해주세요")
    val moodDescription: String = "",

    @field:NotNull(message = "방 분위기 이모지를 선택해주세요")
    val emojiType: EmojiType,
)
