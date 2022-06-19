package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.util.JsonUtil
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class RoomChatSubscriber(
    private val applicationEventPublisher: ApplicationEventPublisher,
) : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val roomChat = JsonUtil.fromByteArray(message.body, RoomChat::class.java)
        val event = RoomChatReceiveEvent(this, roomChat.roomId, roomChat.chat)
        applicationEventPublisher.publishEvent(event)
    }
}
