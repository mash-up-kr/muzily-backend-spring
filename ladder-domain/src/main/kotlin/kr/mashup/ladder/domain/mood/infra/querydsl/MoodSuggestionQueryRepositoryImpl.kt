package kr.mashup.ladder.domain.mood.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.common.domain.CursorSupporter
import kr.mashup.ladder.domain.mood.domain.MoodSuggestion
import kr.mashup.ladder.domain.mood.domain.QMoodSuggestion.moodSuggestion

class MoodSuggestionQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MoodSuggestionQueryRepository {

    override fun findAllByRoomIdLessThanCursorWithLimit(
        roomId: Long,
        cursor: Long?,
        size: Long,
    ): CursorSupporter<MoodSuggestion> {
        if (cursor == null) {
            return fetchMoodSuggestionsFirstCursor(roomId = roomId, size = size)
        }
        return fetchMoodSuggestionsWithCursor(cursor = cursor, roomId = roomId, size = size)
    }

    private fun fetchMoodSuggestionsFirstCursor(roomId: Long, size: Long): CursorSupporter<MoodSuggestion> {
        val items = queryFactory.selectFrom(moodSuggestion)
            .where(
                moodSuggestion.roomId.eq(roomId),
            )
            .orderBy(moodSuggestion.id.desc())
            .limit(size + 1)
            .fetch()
        return CursorSupporter(itemsWithNextCursor = items, cursorSize = size)
    }

    private fun fetchMoodSuggestionsWithCursor(
        cursor: Long?,
        roomId: Long,
        size: Long,
    ): CursorSupporter<MoodSuggestion> {
        val items = queryFactory.selectFrom(moodSuggestion)
            .where(
                moodSuggestion.roomId.eq(roomId),
                moodSuggestion.id.loe(cursor)
            )
            .orderBy(moodSuggestion.id.desc())
            .limit(size + 1)
            .fetch()
        return CursorSupporter(itemsWithNextCursor = items, cursorSize = size)
    }

    override fun findByIdAndRoomId(suggestionId: Long, roomId: Long): MoodSuggestion? {
        return queryFactory.selectFrom(moodSuggestion)
            .where(
                moodSuggestion.id.eq(suggestionId),
                moodSuggestion.roomId.eq(roomId),
            )
            .fetchOne()
    }

}
