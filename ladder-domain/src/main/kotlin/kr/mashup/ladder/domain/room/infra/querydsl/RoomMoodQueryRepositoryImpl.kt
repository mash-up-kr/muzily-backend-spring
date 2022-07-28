package kr.mashup.ladder.domain.room.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.room.domain.QRoomMood.roomMood
import kr.mashup.ladder.domain.room.domain.RoomMood

class RoomMoodQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : RoomMoodQueryRepository {

    override fun findAllByInRoomIds(roomIds: List<Long>): List<RoomMood> {
        return queryFactory.selectFrom(roomMood)
            .where(
                roomMood.roomId.`in`(roomIds)
            ).fetch()
    }

}
