package kr.mashup.ladder.domain.mood.infra.querydsl

import kr.mashup.ladder.domain.common.domain.CursorSupporter
import kr.mashup.ladder.domain.mood.domain.MoodSuggestion

interface MoodSuggestionQueryRepository {

    fun findAllByRoomIdLessThanCursorWithLimit(roomId: Long, cursor: Long?, size: Long): CursorSupporter<MoodSuggestion>

    fun findByIdAndRoomId(suggestionId: Long, roomId: Long): MoodSuggestion?

}
