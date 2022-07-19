package kr.mashup.ladder.room.controller

import kr.mashup.ladder.room.dto.request.RoomSendChatRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest
import kr.mashup.ladder.room.service.RoomService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
class RoomWsController(
    private val roomService: RoomService,
) {
    @MessageMapping("/v1/rooms/{roomId}/send-chat")
    fun sendChat(
        @DestinationVariable roomId: Long,
        @Payload request: RoomSendChatRequest,
    ) {
        roomService.sendChat(roomId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/send-emoji")
    fun sendEmoji(
        @DestinationVariable roomId: Long,
        @Payload request: RoomSendEmojiRequest,
    ) {
        roomService.sendEmoji(roomId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/send-playlist-item-request")
    fun sendPlaylistItemRequest(
        @DestinationVariable roomId: Long,
        @Payload request: RoomSendPlaylistItemRequestRequest,
    ) {
        roomService.sendPlaylistItemRequest(roomId, request)
    }
}
