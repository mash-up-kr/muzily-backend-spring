package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import kr.mashup.ladder.domain.common.error.model.ForbiddenException
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: RoomStatus = RoomStatus.ACTIVE,
) : BaseEntity() {

    @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL], orphanRemoval = true)
    val moods: MutableList<RoomMood> = mutableListOf()

    fun isCreator(memberId: Long): Boolean {
        return this.memberId == memberId
    }

    fun validateCreator(memberId: Long) {
        if (!isCreator(memberId)) {
            throw ForbiddenException("멤버($memberId)는 방($id)의 방장이 아닙니다")
        }
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

    fun delete() {
        this.status = RoomStatus.DELETED
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
