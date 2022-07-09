package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(name = "room_mood")
@Entity
class RoomMood(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    val room: Room,

    @Column(nullable = false)
    val name: String,
) : BaseEntity() {

    fun contain(name: String): Boolean {
        return this.name == name
    }

    companion object {
        fun of(room: Room, name: String): RoomMood {
            return RoomMood(
                room = room,
                name = name,
            )
        }
    }

}
