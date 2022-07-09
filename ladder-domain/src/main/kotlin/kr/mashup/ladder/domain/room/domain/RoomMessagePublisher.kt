package kr.mashup.ladder.domain.room.domain

interface RoomMessagePublisher {
    fun <T> publish(roomTopic: RoomTopic, roomMessage: RoomMessage<T>)
}
