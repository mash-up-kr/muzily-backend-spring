package kr.mashup.ladder.room.listener

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_TOPIC
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlayInformationUpdateMessageReceiveEvent
import kr.mashup.ladder.room.dto.response.RoomPlayInformationUpdateResponse
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class RoomPlayInformationUpdateMessageReceiveEventListener(
    private val simpMessagingTemplate: SimpMessagingTemplate,
) {
    @EventListener
    fun handle(event: RoomPlayInformationUpdateMessageReceiveEvent) {
        val destination = "$WS_DESTINATION_PREFIX_TOPIC/v1/rooms/${event.roomId}"
        val payload = WsResponse.ok(WsResponseType.PLAY_INFORMATION_UPDATE, RoomPlayInformationUpdateResponse(
            event.playlistId,
            event.playlistItemId,
            event.playStatus))
        simpMessagingTemplate.convertAndSend(destination, payload)
    }
}
