package kr.mashup.ladder.mood.dto.request

import kr.mashup.ladder.domain.mood.domain.MoodRecommend
import javax.validation.Valid
import javax.validation.constraints.Size

data class AddMoodRecommendRequest(
    @field:Valid
    @field:Size(max = 10, message = "방 분위기 추천은 최대 10개씩만 가능합니다")
    val names: Set<String>,
) {

    fun toEntity(roomId: Long, memberId: Long): List<MoodRecommend> {
        return names.map { description ->
            MoodRecommend.of(
                roomId = roomId,
                recommenderId = memberId,
                name = description
            )
        }
    }

}
