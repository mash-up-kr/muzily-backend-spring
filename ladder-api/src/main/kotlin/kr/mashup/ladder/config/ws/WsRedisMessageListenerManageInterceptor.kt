package kr.mashup.ladder.config.ws

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
class WsRedisMessageListenerManageInterceptor(
    private val redisMessageListenerContainer: RedisMessageListenerContainer,
    private val roomMessageSubscriber: RoomMessageSubscriber,
) : ChannelInterceptor {

    private val roomSessions: RoomSessions = RoomSessions()

    /**
     * @link https://stackoverflow.com/questions/31634973/stomp-disconnects-its-processing-twice-in-channel-interceptor-and-simplebrokerme
     */
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)
        val destination = accessor.destination
        val sessionId = accessor.sessionId!!
        val command = accessor.command

        when {
            command == StompCommand.SUBSCRIBE && isSubRoom(destination) -> {
                val roomId = getRoomId(destination)
                if (roomSessions.isEmpty(roomId)) {
                    redisMessageListenerContainer.addMessageListener(roomMessageSubscriber, RoomTopic(roomId))
                }
                roomSessions.add(roomId, sessionId)
            }
            command == StompCommand.UNSUBSCRIBE && isSubRoom(destination) -> {
                val roomId = getRoomId(destination)
                roomSessions.remove(roomId, sessionId)
                if (roomSessions.isEmpty(roomId)) {
                    redisMessageListenerContainer.removeMessageListener(roomMessageSubscriber, RoomTopic(roomId))
                }
            }
            command == StompCommand.DISCONNECT -> {
                roomSessions.remove(sessionId)
            }
            else -> {}
        }

        return message
    }

    private fun isSubRoom(destination: String?): Boolean {
        return destination?.matches(Regex("^/sub/v1/rooms/\\d+$")) ?: false
    }

    private fun getRoomId(destination: String?): Long {
        destination ?: throw IllegalArgumentException()
        val roomIdStr = destination.replace("/sub/v1/rooms/", "")
        return roomIdStr.toLong()
    }
}

private class RoomSessions {
    private val sessionIdsByRoomId: MutableMap<Long, MutableSet<String>> = mutableMapOf()

    fun isEmpty(roomId: Long): Boolean {
        val sessionIds = sessionIdsByRoomId[roomId]
        return sessionIds?.isEmpty() ?: true
    }

    fun add(roomId: Long, sessionId: String) {
        val sessionIds = sessionIdsByRoomId.getOrDefault(roomId, mutableSetOf())
        sessionIds.add(sessionId)
        sessionIdsByRoomId[roomId] = sessionIds
    }

    fun remove(roomId: Long, sessionId: String) {
        val sessionIds = sessionIdsByRoomId.getOrDefault(roomId, mutableSetOf())
        sessionIds.remove(sessionId)
        sessionIdsByRoomId[roomId] = sessionIds
    }

    fun remove(sessionId: String) {
        sessionIdsByRoomId.forEach { e -> e.value.remove(sessionId) }
    }
}
