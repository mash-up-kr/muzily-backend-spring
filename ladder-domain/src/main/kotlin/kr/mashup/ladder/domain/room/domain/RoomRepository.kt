package kr.mashup.ladder.domain.room.domain

interface RoomRepository {
    fun save(room: Room): Room
    fun findById(roomId: Long): Room?
}
