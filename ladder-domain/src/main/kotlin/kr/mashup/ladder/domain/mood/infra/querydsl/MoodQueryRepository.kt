package kr.mashup.ladder.domain.mood.infra.querydsl

import kr.mashup.ladder.domain.mood.domain.Mood

interface MoodQueryRepository {

    fun findAllByInRoomIds(roomIds: List<Long>): List<Mood>

}
