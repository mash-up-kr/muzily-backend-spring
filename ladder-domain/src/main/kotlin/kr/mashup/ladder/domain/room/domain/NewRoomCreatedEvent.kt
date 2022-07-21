package kr.mashup.ladder.domain.room.domain

data class NewRoomCreatedEvent(
    val roomId: Long,
) {

    companion object {
        fun of(room: Room): NewRoomCreatedEvent {
            return NewRoomCreatedEvent(roomId = room.id)
        }
    }

}
