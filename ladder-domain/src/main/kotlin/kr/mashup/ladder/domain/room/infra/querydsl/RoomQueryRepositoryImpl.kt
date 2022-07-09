package kr.mashup.ladder.domain.room.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.room.domain.QRoom.room
import kr.mashup.ladder.domain.room.domain.QRoomMood.roomMood
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomStatus

class RoomQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : RoomQueryRepository {

    override fun findRoomByInvitationKey(invitationKey: String): Room? {
        return queryFactory.selectFrom(room)
            .where(
                room.invitationKey.invitationKey.eq(invitationKey),
                room.status.eq(RoomStatus.ACTIVE),
            ).fetchOne()
    }

    override fun existsRoomByMemberId(memberId: Long): Boolean {
        return queryFactory.selectOne()
            .from(room)
            .where(
                room.memberId.eq(memberId),
                room.status.eq(RoomStatus.ACTIVE),
            ).fetchFirst() != null
    }

    override fun findRoomsByMemberId(memberId: Long): List<Room> {
        return queryFactory.selectFrom(room)
            .leftJoin(room.moods, roomMood).fetchJoin()
            .where(
                room.memberId.eq(memberId),
                room.status.eq(RoomStatus.ACTIVE),
            ).fetch()
    }

    override fun findRoomById(roomId: Long): Room? {
        return queryFactory.selectFrom(room)
            .leftJoin(room.moods, roomMood).fetchJoin()
            .where(
                room.id.eq(roomId),
                room.status.eq(RoomStatus.ACTIVE),
            ).fetchOne()
    }

}

