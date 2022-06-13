package kr.mashup.ladder.domain.room.domain

import java.util.*

interface RoomRepository {
    fun save(room: Room): Room
    fun findById(id: Long): Optional<Room>
}
