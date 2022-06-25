package kr.mashup.ladder.domain.room.infra.jpa

import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomRepository
import org.springframework.data.jpa.repository.JpaRepository

interface RoomJpaRepository : JpaRepository<Room, Long>, RoomRepository
