package kr.mashup.ladder.domain.room.infra.redis

import kr.mashup.ladder.domain.room.domain.RoomMessage
import kr.mashup.ladder.domain.room.domain.RoomMessagePublisher
import kr.mashup.ladder.domain.room.domain.RoomTopic
import kr.mashup.ladder.common.util.JsonUtil
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RoomMessageRedisPublisher(
    private val redisTemplate: RedisTemplate<String, String>,
) : RoomMessagePublisher {
    override fun <T> publish(roomTopic: RoomTopic, roomMessage: RoomMessage<T>) {
        redisTemplate.convertAndSend(roomTopic.topic, JsonUtil.toJson(roomMessage))
    }
}
