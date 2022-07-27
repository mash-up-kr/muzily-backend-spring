package kr.mashup.ladder.room.controller

import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.room.dto.request.RoomAcceptPlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomAddPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomSendChatRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest
import kr.mashup.ladder.room.service.RoomWsService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
class RoomWsController(
    private val roomWsService: RoomWsService,
) {
    @MessageMapping("/v1/rooms/{roomId}/send-chat")
    fun sendChat(
        @DestinationVariable roomId: Long,
        @Payload request: RoomSendChatRequest,
    ) {
        roomWsService.sendChat(roomId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/send-emoji")
    fun sendEmoji(
        @DestinationVariable roomId: Long,
        @Payload request: RoomSendEmojiRequest,
    ) {
        roomWsService.sendEmoji(roomId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/send-playlist-item-request")
    fun sendPlaylistItemRequest(
        @DestinationVariable roomId: Long,
        @Payload request: RoomSendPlaylistItemRequestRequest,
    ) {
        roomWsService.sendPlaylistItemRequest(roomId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/accept-playlist-item-request")
    fun acceptPlaylistItemRequest(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomAcceptPlaylistItemRequestRequest,
    ) {
        roomWsService.acceptPlaylistItemRequest(roomId, memberId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/add-playlist-item")
    fun addPlaylistItem(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomAddPlaylistItemRequest,
    ) {
        roomWsService.addPlaylistItem(roomId, memberId, request)
    }
}
