package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(name = "room_member_mapper")
@Entity
class RoomMemberMapper(
    @JoinColumn(name = "room_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val room: Room,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: RoomRole,
) : BaseEntity() {

    companion object {
        fun newAdmin(room: Room, memberId: Long): RoomMemberMapper {
            return RoomMemberMapper(
                room = room,
                memberId = memberId,
                role = RoomRole.CREATOR,
            )
        }

        fun newGuest(room: Room, memberId: Long): RoomMemberMapper {
            return RoomMemberMapper(
                room = room,
                memberId = memberId,
                role = RoomRole.GUEST,
            )
        }
    }

}
