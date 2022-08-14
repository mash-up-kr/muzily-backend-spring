package kr.mashup.ladder.domain.mood.infra.querydsl

import kr.mashup.ladder.domain.common.domain.CursorSupporter
import kr.mashup.ladder.domain.mood.domain.MoodRecommend

interface MoodRecommendQueryRepository {

    fun findAllByRoomIdLessThanCursorWithLimit(roomId: Long, cursor: Long?, size: Long): CursorSupporter<MoodRecommend>

    fun findByIdAndRoomId(recommendId: Long, roomId: Long): MoodRecommend?

}
