package kr.mashup.ladder.mood.dto.request

import kr.mashup.ladder.domain.mood.domain.MoodRecommend
import javax.validation.Valid
import javax.validation.constraints.Size

data class AddMoodRecommendRequest(
    @field:Valid
    @field:Size(max = 10)
    val descriptions: Set<String>,
) {

    fun toEntity(roomId: Long, memberId: Long): List<MoodRecommend> {
        return descriptions.map { description ->
            MoodRecommend(
                roomId = roomId,
                recommenderId = memberId,
                description = description
            )
        }
    }

}
