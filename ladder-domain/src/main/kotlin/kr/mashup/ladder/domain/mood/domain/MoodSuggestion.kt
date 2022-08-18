package kr.mashup.ladder.domain.mood.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "mood_suggestion")
@Entity
class MoodSuggestion(
    val roomId: Long,
    val name: String,
    val recommenderId: Long,
) : BaseEntity() {

    companion object {
        fun of(
            roomId: Long,
            name: String,
            recommenderId: Long,
        ) = MoodSuggestion(
            roomId = roomId,
            name = name,
            recommenderId = recommenderId,
        )
    }

}
