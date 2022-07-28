package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "room_mood")
@Entity
class RoomMood(
    val roomId: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val emoji: String,
) : BaseEntity() {

    companion object {
        fun of(roomId: Long, name: String, emoji: String): RoomMood {
            return RoomMood(
                roomId = roomId,
                name = name,
                emoji = emoji,
            )
        }
    }

}
