package kr.mashup.ladder.room.listener

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_TOPIC
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemNotFoundException
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemRepository
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemAddMessageReceiveEvent
import kr.mashup.ladder.room.dto.response.RoomPlaylistItemAddResponse
import org.springframework.context.event.EventListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class RoomPlaylistItemAddMessageReceiveEventListener(
    private val simpMessagingTemplate: SimpMessagingTemplate,
    private val playlistItemRepository: PlaylistItemRepository,
) {
    @EventListener
    fun handle(event: RoomPlaylistItemAddMessageReceiveEvent) {
        val item = playlistItemRepository.findByIdOrNull(event.playlistItemId)
            ?: throw PlaylistItemNotFoundException("${event.playlistItemId}")

        val destination = "$WS_DESTINATION_PREFIX_TOPIC/v1/rooms/${event.roomId}"
        val payload = WsResponse.ok(WsResponseType.PLAYLIST_ITEM_ADD, RoomPlaylistItemAddResponse.of(item))
        simpMessagingTemplate.convertAndSend(destination, payload)
    }
}
