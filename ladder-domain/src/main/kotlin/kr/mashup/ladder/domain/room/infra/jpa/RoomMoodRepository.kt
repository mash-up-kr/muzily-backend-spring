package kr.mashup.ladder.domain.room.infra.jpa

import kr.mashup.ladder.domain.room.domain.RoomMood
import org.springframework.data.jpa.repository.JpaRepository

interface RoomMoodRepository : JpaRepository<RoomMood, Long>
