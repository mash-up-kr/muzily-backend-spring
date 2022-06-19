package kr.mashup.ladder.domain.room.domain

import org.springframework.context.ApplicationEvent

class RoomChatReceiveEvent(
    source: Any,
    val roomId: Long,
    val chat: String
) : ApplicationEvent(source)
