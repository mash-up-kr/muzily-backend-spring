package kr.mashup.ladder.room.listener

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.config.context.WsMemberPrincipalContext
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_QUEUE
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemRepository
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemRequestMessageReceiveEvent
import kr.mashup.ladder.room.dto.response.RoomPlaylistItemRequestResponse
import kr.mashup.ladder.room.service.RoomService
import org.springframework.context.event.EventListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class RoomPlaylistItemRequestMessageReceiveEventListener(
    private val simpMessagingTemplate: SimpMessagingTemplate,
    private val roomService: RoomService,
    private val playlistItemRepository: PlaylistItemRepository,
) {
    @EventListener
    fun handle(event: RoomPlaylistItemRequestMessageReceiveEvent) {
        val room = roomService.findRoomById(event.roomId)
        val item = playlistItemRepository.findByIdOrNull(event.playlistItemId) ?: throw IllegalStateException()
        val principal = WsMemberPrincipalContext.get(room.getCreator())
        val payload = WsResponse.ok(WsResponseType.PLAYLIST_ITEM_REQUEST, RoomPlaylistItemRequestResponse.of(item))
        simpMessagingTemplate.convertAndSendToUser(principal!!.name, "$WS_DESTINATION_PREFIX_QUEUE", payload)
    }
}
