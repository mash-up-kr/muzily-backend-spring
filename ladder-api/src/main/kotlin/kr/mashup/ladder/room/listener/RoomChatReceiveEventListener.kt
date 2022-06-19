package kr.mashup.ladder.room.listener

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.domain.room.domain.RoomChatReceiveEvent
import kr.mashup.ladder.room.dto.RoomChatResponse
import org.springframework.context.ApplicationListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class RoomChatReceiveEventListener(
    private val simpMessagingTemplate: SimpMessagingTemplate,
) : ApplicationListener<RoomChatReceiveEvent> {
    override fun onApplicationEvent(event: RoomChatReceiveEvent) {
        val destination = "/sub/v1/rooms/${event.roomId}"
        val payload = WsResponse.ok(WsResponseType.CHAT, RoomChatResponse(event.chat))
        simpMessagingTemplate.convertAndSend(destination, payload)
    }
}
