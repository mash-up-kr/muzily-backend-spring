package kr.mashup.ladder.room.listener

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.config.context.WsMemberPrincipalContext
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_QUEUE
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemRequestDeclineMessageReceiveEvent
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.room.dto.response.RoomPlaylistItemRequestDeclineResponse
import kr.mashup.ladder.room.service.RoomServiceHelper
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class RoomPlaylistItemRequestDeclineMessageReceiveEventListener(
    private val simpMessagingTemplate: SimpMessagingTemplate,
    private val roomRepository: RoomRepository,
) {
    @EventListener
    fun handle(event: RoomPlaylistItemRequestDeclineMessageReceiveEvent) {
        val room = RoomServiceHelper.findRoomByIdFetchMember(roomRepository, event.roomId)
        val principals = WsMemberPrincipalContext.get(room.getCreator())
        val payload = WsResponse.ok(
            WsResponseType.PLAYLIST_ITEM_REQUEST_DECLINE,
            RoomPlaylistItemRequestDeclineResponse(event.playlistId, event.playlistItemId))
        principals.forEach {
            simpMessagingTemplate.convertAndSendToUser(it.name, WS_DESTINATION_PREFIX_QUEUE, payload)
        }
    }
}
