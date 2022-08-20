package kr.mashup.ladder.domain.mood.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "mood_suggestion")
@Entity
class MoodSuggestion(
    @Column(nullable = false)
    val roomId: Long,

    @Column(nullable = false)
    val recommenderId: Long,

    @Column(nullable = false, length = 30)
    val name: String,
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
