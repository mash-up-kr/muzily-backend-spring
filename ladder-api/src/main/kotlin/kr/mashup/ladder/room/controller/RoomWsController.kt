package kr.mashup.ladder.room.controller

import kr.mashup.ladder.room.dto.RoomChatPayload
import kr.mashup.ladder.room.service.RoomService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

// TODO : api + ws 통합 테스트 작성, 에러 응답 처리
@Controller
class RoomWsController(
    private val roomService: RoomService,
) {
    @MessageMapping("/pub/rooms/{roomId}/chats")
    fun publishChat(@DestinationVariable roomId: Long, payload: RoomChatPayload) {
        roomService.publishChat(roomId, payload.chat)
    }
}
