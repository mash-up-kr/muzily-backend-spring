package kr.mashup.ladder.room.controller

import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.room.dto.request.RoomSendChatRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
import kr.mashup.ladder.room.service.RoomService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class RoomWsController(
    private val roomService: RoomService,
) {
    @MessageMapping("/pub/v1/rooms/{roomId}/enter")
    fun enterRoom(@DestinationVariable roomId: Long, @MemberId memberId: Long) {
        roomService.enterRoom(roomId = roomId, memberId = memberId)
    }

    @MessageMapping("/pub/v1/rooms/{roomId}/leave")
    fun leaveRoom(@DestinationVariable roomId: Long, @MemberId memberId: Long) {
        roomService.leave(roomId = roomId, memberId = memberId)
    }

    @MessageMapping("/pub/v1/rooms/{roomId}/send-chat")
    fun sendChat(@DestinationVariable roomId: Long, request: RoomSendChatRequest) {
        roomService.sendChat(roomId, request)
    }

    @MessageMapping("/pub/v1/rooms/{roomId}/send-emoji")
    fun sendEmoji(@DestinationVariable roomId: Long, request: RoomSendEmojiRequest) {
        roomService.sendEmoji(roomId, request)
    }
}
