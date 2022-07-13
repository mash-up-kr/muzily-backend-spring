package kr.mashup.ladder.config.interceptor

import kr.mashup.ladder.config.context.WsRoomSessionContext
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_TOPIC
import kr.mashup.ladder.domain.room.domain.RoomMessageSubscriber
import kr.mashup.ladder.domain.room.domain.RoomTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class WsRoomSessionContextManageInterceptor(
    private val redisMessageListenerContainer: RedisMessageListenerContainer,
    private val roomMessageSubscriber: RoomMessageSubscriber,
) : ChannelInterceptor {
    /**
     * @link https://stackoverflow.com/questions/31634973/stomp-disconnects-its-processing-twice-in-channel-interceptor-and-simplebrokerme
     */
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)
        val destination = accessor.destination
        val sessionId = accessor.sessionId!!
        val command = accessor.command

        when {
            command == StompCommand.SUBSCRIBE && destination.isRoom() -> {
                val roomId = destination.getRoomId()
                if (WsRoomSessionContext.isEmpty(roomId)) {
                    redisMessageListenerContainer.addMessageListener(roomMessageSubscriber, RoomTopic(roomId))
                }
                WsRoomSessionContext.add(roomId, sessionId)
            }
            command == StompCommand.UNSUBSCRIBE && destination.isRoom() -> {
                val roomId = destination.getRoomId()
                WsRoomSessionContext.remove(roomId, sessionId)
                if (WsRoomSessionContext.isEmpty(roomId)) {
                    redisMessageListenerContainer.removeMessageListener(roomMessageSubscriber, RoomTopic(roomId))
                }
            }
            command == StompCommand.DISCONNECT -> {
                WsRoomSessionContext.remove(sessionId)
            }
            else -> {}
        }

        return message
    }
}

private fun String?.isRoom(): Boolean {
    return this?.matches(Regex("^${WS_DESTINATION_PREFIX_TOPIC}/v1/rooms/\\d+$")) ?: false
}

private fun String?.getRoomId(): Long {
    this ?: throw IllegalArgumentException()
    val roomIdStr = this.replace("${WS_DESTINATION_PREFIX_TOPIC}/v1/rooms/", "")
    return roomIdStr.toLong()
}
