package kr.mashup.ladder.room.listener

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_TOPIC
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemAddMessageReceiveEvent
import kr.mashup.ladder.room.dto.response.RoomPlaylistItemRemoveResponse
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class RoomPlaylistItemRemoveMessageReceiveEventListener(
    private val simpMessagingTemplate: SimpMessagingTemplate,
) {
    @EventListener
    fun handle(event: RoomPlaylistItemAddMessageReceiveEvent) {
        val destination = "$WS_DESTINATION_PREFIX_TOPIC/v1/rooms/${event.roomId}"
        val payload = WsResponse.ok(
            WsResponseType.PLAYLIST_ITEM_REMOVE,
            RoomPlaylistItemRemoveResponse(playlistId = event.playlistId, playlistItemId = event.playlistItemId))
        simpMessagingTemplate.convertAndSend(destination, payload)
    }
}
