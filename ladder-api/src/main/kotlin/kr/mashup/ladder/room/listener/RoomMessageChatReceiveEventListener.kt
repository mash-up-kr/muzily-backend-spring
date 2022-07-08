package kr.mashup.ladder.room.listener

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.domain.room.domain.RoomMessageChatReceiveEvent
import kr.mashup.ladder.room.dto.response.RoomChatResponse
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class RoomMessageChatReceiveEventListener(
    private val simpMessagingTemplate: SimpMessagingTemplate,
) {
    @EventListener
    fun handle(event: RoomMessageChatReceiveEvent) {
        val destination = "/sub/v1/rooms/${event.roomId}"
        val payload = WsResponse.ok(WsResponseType.CHAT, RoomChatResponse(event.chat))
        simpMessagingTemplate.convertAndSend(destination, payload)
    }
}
