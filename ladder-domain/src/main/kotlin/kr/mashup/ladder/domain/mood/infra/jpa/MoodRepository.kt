package kr.mashup.ladder.domain.mood.infra.jpa

import kr.mashup.ladder.domain.mood.domain.Mood
import kr.mashup.ladder.domain.mood.infra.querydsl.MoodQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface MoodRepository : JpaRepository<Mood, Long>, MoodQueryRepository {

    fun findAllByRoomId(roomId: Long): List<Mood>

}
