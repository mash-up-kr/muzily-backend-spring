package kr.mashup.ladder.room.controller

import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.room.dto.request.RoomSendChatRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequest
import kr.mashup.ladder.room.service.RoomService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
class RoomWsController(
    private val roomService: RoomService,
) {
    @MessageMapping("/pub/v1/rooms/{roomId}/send-chat")
    fun sendChat(
        @DestinationVariable roomId: Long,
        @Payload request: RoomSendChatRequest,
        @MemberId memberId: Long,
    ) {
        roomService.sendChat(roomId, request)
    }

    @MessageMapping("/pub/v1/rooms/{roomId}/send-emoji")
    fun sendEmoji(
        @DestinationVariable roomId: Long,
        @Payload request: RoomSendEmojiRequest,
        @MemberId memberId: Long,
    ) {
        roomService.sendEmoji(roomId, request)
    }

    @MessageMapping("/pub/v1/rooms/{roomId}/send-playlist-item-request")
    fun sendPlaylistItemRequest(
        @DestinationVariable roomId: Long,
        @Payload request: RoomSendPlaylistItemRequest,
        @MemberId memberId: Long,
    ) {
        roomService.sendPlaylistItemRequest(roomId, request)
    }
}
