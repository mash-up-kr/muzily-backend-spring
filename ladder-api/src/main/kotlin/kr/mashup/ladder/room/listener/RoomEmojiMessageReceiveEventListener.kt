package kr.mashup.ladder.room.listener

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.domain.room.domain.emoji.RoomEmojiMessageRecieveEvent
import kr.mashup.ladder.room.dto.response.RoomEmojiResponse
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class RoomEmojiMessageReceiveEventListener(
    private val simpMessagingTemplate: SimpMessagingTemplate,
) {
    @EventListener
    fun handle(event: RoomEmojiMessageRecieveEvent) {
        val destination = "/sub/v1/rooms/${event.roomId}"
        val payload = WsResponse.ok(WsResponseType.EMOJI, RoomEmojiResponse(event.emojiType))
        simpMessagingTemplate.convertAndSend(destination, payload)
    }
}
