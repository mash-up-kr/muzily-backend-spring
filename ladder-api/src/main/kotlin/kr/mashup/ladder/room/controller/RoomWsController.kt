package kr.mashup.ladder.room.controller

import kr.mashup.ladder.room.dto.RoomChatRequest
import kr.mashup.ladder.room.service.RoomService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class RoomWsController(
    private val roomService: RoomService,
) {
    @MessageMapping("/pub/v1/rooms/{roomId}/chats")
    fun publishChat(@DestinationVariable roomId: Long, request: RoomChatRequest) {
        roomService.publishChat(roomId, request.chat)
    }
}
