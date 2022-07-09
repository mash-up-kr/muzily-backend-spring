package kr.mashup.ladder.domain.room.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.room.domain.QRoom.room
import kr.mashup.ladder.domain.room.domain.Room

class RoomQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : RoomQueryRepository {

    override fun findRoomByInvitationKey(invitationKey: String): Room? {
        return queryFactory.selectFrom(room)
            .where(
                room.invitationKey.invitationKey.eq(invitationKey)
            ).fetchOne()
    }

    override fun existsRoomByMemberId(memberId: Long): Boolean {
        return queryFactory.selectOne()
            .from(room)
            .where(
                room.memberId.eq(memberId)
            ).fetchFirst() != null
    }

    override fun findRoomByMemberId(memberId: Long): Room? {
        return queryFactory.selectFrom(room)
            .where(
                room.memberId.eq(memberId)
            ).fetchOne()
    }

}

