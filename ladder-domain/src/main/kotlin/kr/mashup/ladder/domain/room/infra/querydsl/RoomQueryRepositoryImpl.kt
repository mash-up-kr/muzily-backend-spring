package kr.mashup.ladder.domain.room.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.room.domain.QRoom.room
import kr.mashup.ladder.domain.room.domain.QRoomMemberMapper.roomMemberMapper
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomRole
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

    override fun existsRoomByCreatorId(memberId: Long): Boolean {
        return queryFactory.selectOne()
            .from(room)
            .innerJoin(roomMemberMapper)
            .on(roomMemberMapper.room.id.eq(room.id))
            .where(
                roomMemberMapper.memberId.eq(memberId),
                roomMemberMapper.role.eq(RoomRole.CREATOR),
                room.status.eq(RoomStatus.ACTIVE),
            ).fetchFirst() != null
    }

    override fun findRoomIdByCreatorId(creatorId: Long): Long? {
        return queryFactory.select(room.id)
            .from(room)
            .innerJoin(roomMemberMapper)
            .on(roomMemberMapper.room.id.eq(room.id))
            .where(
                roomMemberMapper.memberId.eq(creatorId),
                roomMemberMapper.role.eq(RoomRole.CREATOR),
                room.status.eq(RoomStatus.ACTIVE),
            ).fetchOne()
    }

    override fun findRoomById(roomId: Long): Room? {
        return queryFactory.selectFrom(room)
            .where(
                room.id.eq(roomId),
                room.status.eq(RoomStatus.ACTIVE),
            ).fetchOne()
    }

    override fun findRoomByIdWithFetchMember(roomId: Long): Room? {
        return queryFactory.selectFrom(room)
            .innerJoin(room.participants, roomMemberMapper).fetchJoin()
            .where(
                room.id.eq(roomId),
                room.status.eq(RoomStatus.ACTIVE),
            ).fetchOne()
    }

    override fun existsRoomByIdAndCreatorId(roomId: Long, creatorId: Long): Boolean {
        return queryFactory.selectOne()
            .from(room)
            .innerJoin(roomMemberMapper)
            .on(roomMemberMapper.room.id.eq(room.id))
            .where(
                roomMemberMapper.memberId.eq(creatorId),
                roomMemberMapper.role.eq(RoomRole.CREATOR),
                room.status.eq(RoomStatus.ACTIVE),
            ).fetchFirst() != null
    }

    override fun existsRoomByIdAndMemberId(roomId: Long, creatorId: Long): Boolean {
        return queryFactory.selectOne()
            .from(room)
            .innerJoin(roomMemberMapper)
            .on(roomMemberMapper.room.id.eq(room.id))
            .where(
                roomMemberMapper.memberId.eq(creatorId),
                room.status.eq(RoomStatus.ACTIVE),
            ).fetchFirst() != null
    }

}

