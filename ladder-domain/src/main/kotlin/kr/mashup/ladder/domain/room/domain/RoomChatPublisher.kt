package kr.mashup.ladder.domain.room.domain

interface RoomChatPublisher {
    fun publish(roomChat: RoomChat)
}
