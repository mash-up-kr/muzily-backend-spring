package kr.mashup.ladder.room.dto.request

import kr.mashup.ladder.domain.mood.domain.Mood
import kr.mashup.ladder.domain.room.domain.emoji.EmojiType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class RoomMoodRequest(
    @field:NotBlank(message = "분위기 명을 입력해주세요")
    val name: String = "",
    @field:NotNull(message = "분위기 이모지 종류를 선택해주세요")
    val emojiType: EmojiType,
) {

    fun toEntity(roomId: Long) = Mood.of(
        roomId = roomId,
        name = name,
        emojiType = emojiType,
    )

}
