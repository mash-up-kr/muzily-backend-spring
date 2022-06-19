package kr.mashup.ladder.domain.room.infra.redis

import kr.mashup.ladder.domain.room.domain.RoomChat
import kr.mashup.ladder.domain.room.domain.RoomChatPublisher
import kr.mashup.ladder.domain.util.JsonUtil
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class RoomChatRedisPublisher(
    private val redisTemplate: RedisTemplate<String, String>,
    private val roomChatChannelTopic: ChannelTopic,
) : RoomChatPublisher {
    override fun publish(roomChat: RoomChat) {
        val channel = roomChatChannelTopic.topic
        val message = JsonUtil.toJson(roomChat)
        redisTemplate.convertAndSend(channel, message)
    }
}
