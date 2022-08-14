package kr.mashup.ladder.domain.mood.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.mood.domain.Mood
import kr.mashup.ladder.domain.mood.domain.QMood.mood

class MoodQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MoodQueryRepository {

    override fun findAllByInRoomIds(roomIds: List<Long>): List<Mood> {
        return queryFactory.selectFrom(mood)
            .where(
                mood.roomId.`in`(roomIds)
            ).fetch()
    }

}
