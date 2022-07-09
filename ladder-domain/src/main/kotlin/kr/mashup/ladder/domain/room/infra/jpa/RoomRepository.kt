package kr.mashup.ladder.domain.room.infra.jpa

import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.infra.querydsl.RoomQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<Room, Long>, RoomQueryRepository
