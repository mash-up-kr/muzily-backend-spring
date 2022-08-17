package kr.mashup.ladder.domain.mood.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.common.domain.CursorSupporter
import kr.mashup.ladder.domain.mood.domain.MoodRecommend
import kr.mashup.ladder.domain.mood.domain.QMoodRecommend.moodRecommend

class MoodRecommendQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MoodRecommendQueryRepository {

    override fun findAllByRoomIdLessThanCursorWithLimit(
        roomId: Long,
        cursor: Long?,
        size: Long,
    ): CursorSupporter<MoodRecommend> {
        if (cursor == null) {
            return fetchMoodRecommendsFirstCursor(roomId = roomId, size = size)
        }
        return fetchMoodWithCursor(cursor = cursor, roomId = roomId, size = size)
    }

    private fun fetchMoodRecommendsFirstCursor(roomId: Long, size: Long): CursorSupporter<MoodRecommend> {
        val items = queryFactory.selectFrom(moodRecommend)
            .where(
                moodRecommend.roomId.eq(roomId),
            )
            .orderBy(moodRecommend.id.desc())
            .limit(size + 1)
            .fetch()
        return CursorSupporter(itemsWithNextCursor = items, cursorSize = size)
    }

    private fun fetchMoodWithCursor(cursor: Long?, roomId: Long, size: Long): CursorSupporter<MoodRecommend> {
        val items = queryFactory.selectFrom(moodRecommend)
            .where(
                moodRecommend.roomId.eq(roomId),
                moodRecommend.id.loe(cursor)
            )
            .orderBy(moodRecommend.id.desc())
            .limit(size + 1)
            .fetch()
        return CursorSupporter(itemsWithNextCursor = items, cursorSize = size)
    }

    override fun findByIdAndRoomId(recommendId: Long, roomId: Long): MoodRecommend? {
        return queryFactory.selectFrom(moodRecommend)
            .where(
                moodRecommend.id.eq(recommendId),
                moodRecommend.roomId.eq(roomId),
            )
            .fetchOne()
    }

}
