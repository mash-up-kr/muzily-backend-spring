package kr.mashup.ladder.domain.mood.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "mood")
@Entity
class Mood(
    val roomId: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val emoji: String,
) : BaseEntity() {

    companion object {
        fun of(roomId: Long, name: String, emoji: String): Mood {
            return Mood(
                roomId = roomId,
                name = name,
                emoji = emoji,
            )
        }
    }

}
