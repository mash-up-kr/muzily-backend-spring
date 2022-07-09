package kr.mashup.ladder.domain.room.domain

import org.springframework.data.redis.listener.ChannelTopic

class RoomTopic(roomId: Long) : ChannelTopic("room.${roomId}")
