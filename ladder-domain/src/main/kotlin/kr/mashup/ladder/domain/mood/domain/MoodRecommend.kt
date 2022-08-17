package kr.mashup.ladder.domain.mood.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "mood_recommend")
@Entity
class MoodRecommend(
    val roomId: Long,
    val description: String,
    val recommenderId: Long,
) : BaseEntity() {

    companion object {
        fun of(
            roomId: Long,
            description: String,
            recommenderId: Long,
        ): MoodRecommend {
            return MoodRecommend(
                roomId = roomId,
                description = description,
                recommenderId = recommenderId,
            )
        }
    }

}
