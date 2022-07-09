package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Table(name = "room")
@Entity
class Room(
    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    var description: String,

    @Embedded
    var invitationKey: InvitationKey,
) : BaseEntity() {

    @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL], orphanRemoval = true)
    val moods: MutableList<RoomMood> = mutableListOf()

    fun isCreator(memberId: Long): Boolean {
        return this.memberId == memberId
    }

    fun update(description: String) {
        this.description = description
    }

    fun updateMoods(moodNames: Set<String>) {
        val removedMoods = this.moods.filter { mood -> !moodNames.contains(mood.name) }
        this.moods.removeAll(removedMoods)

        val newMoods = moodNames.filter { moodName -> !this.moods.any { mood -> mood.contain(moodName) } }
            .map { mood -> RoomMood.of(room = this, name = mood) }
        this.moods.addAll(newMoods)
    }

    companion object {
        fun newInstance(description: String, memberId: Long): Room {
            return Room(
                memberId = memberId,
                description = description,
                invitationKey = InvitationKey.newInstance()
            )
        }
    }

}
