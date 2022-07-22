package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import kr.mashup.ladder.domain.common.exception.model.ForbiddenException
import kr.mashup.ladder.domain.room.exception.RoomMemberConflictException
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
    var description: String,

    @Embedded
    var invitationKey: InvitationKey,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: RoomStatus = RoomStatus.ACTIVE,
) : BaseEntity() {

    @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL], orphanRemoval = true)
    val moods: MutableList<RoomMood> = mutableListOf()

    @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL], orphanRemoval = true)
    val participants: MutableList<RoomMemberMapper> = mutableListOf()

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

    fun addCreator(memberId: Long) {
        validateNotParticipant(memberId)
        this.participants.add(RoomMemberMapper.newAdmin(room = this, memberId = memberId))
    }

    fun addGuest(memberId: Long) {
        validateNotParticipant(memberId)
        this.participants.add(RoomMemberMapper.newGuest(room = this, memberId = memberId))
    }

    fun getRole(memberId: Long): RoomRole? {
        return findParticipant(memberId)?.role
    }

    fun isCreator(memberId: Long): Boolean {
        return findParticipant(memberId)?.role == RoomRole.CREATOR
    }

    fun isParticipant(memberId: Long): Boolean {
        return findParticipant(memberId) != null
    }

    private fun findParticipant(memberId: Long): RoomMemberMapper? {
        return this.participants.find { participant -> participant.memberId == memberId }
    }

    fun getCreator(): Long {
        return this.participants.find { participant -> participant.role == RoomRole.CREATOR }?.memberId
            ?: throw IllegalArgumentException("방(${id})에 대한 생성자가 존재하지 않습니다")
    }

    fun validateCreator(memberId: Long) {
        if (!isCreator(memberId)) {
            throw ForbiddenException("멤버($memberId)는 방($id)의 방장이 아닙니다")
        }
    }

    fun validateParticipant(memberId: Long) {
        if (!isParticipant(memberId)) {
            throw ForbiddenException("멤버(${memberId})는 해당 방(${id})에 참여하고 있지 않습니다")
        }
    }

    private fun validateNotParticipant(memberId: Long) {
        if (isParticipant(memberId)) {
            throw RoomMemberConflictException("이미 해당하는 방(${id})에 참여하고 있는 멤버(${memberId}) 입니다")
        }
    }

    companion object {
        fun newInstance(description: String, memberId: Long): Room {
            val room = Room(
                description = description,
                invitationKey = InvitationKey.newInstance()
            )
            room.addCreator(memberId)
            return room
        }
    }

}
