package kr.mashup.ladder.domain.room.infra.jpa

import kr.mashup.ladder.domain.room.domain.RoomMood
import kr.mashup.ladder.domain.room.infra.querydsl.RoomMoodQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface RoomMoodRepository : JpaRepository<RoomMood, Long>, RoomMoodQueryRepository {

    fun findAllByRoomId(roomId: Long): List<RoomMood>

}
