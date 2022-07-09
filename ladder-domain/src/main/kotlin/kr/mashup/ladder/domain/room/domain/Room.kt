package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
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

    fun isCreator(memberId: Long): Boolean {
        return this.memberId == memberId
    }

    fun update(description: String) {
        this.description = description
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
