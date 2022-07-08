package kr.mashup.ladder.domain.room.domain

import com.fasterxml.jackson.core.type.TypeReference
import kr.mashup.ladder.domain.util.JsonUtil
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class RoomMessageSubscriber(
    private val applicationEventPublisher: ApplicationEventPublisher,
) : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        when (JsonUtil.fromByteArray(message.body, RoomMessage::class.java).type) {
            RoomMessageType.CHAT -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomMessageChat>>() {})
                applicationEventPublisher.publishEvent(RoomMessageChatReceiveEvent(
                    roomMessage.data.roomId,
                    roomMessage.data.chat))
            }
            // TODO : add more room message type (ex. add playlist item, delete playlist item, ...)
        }
    }
}
