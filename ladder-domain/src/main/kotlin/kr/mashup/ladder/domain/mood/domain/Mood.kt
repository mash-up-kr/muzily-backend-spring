package kr.mashup.ladder.domain.mood.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import kr.mashup.ladder.domain.room.domain.emoji.EmojiType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Table(name = "mood")
@Entity
class Mood(
    val roomId: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val emojiType: EmojiType,
) : BaseEntity() {

    companion object {
        fun of(
            roomId: Long,
            name: String,
            emojiType: EmojiType,
        ) = Mood(
            roomId = roomId,
            name = name,
            emojiType = emojiType
        )
    }

}
