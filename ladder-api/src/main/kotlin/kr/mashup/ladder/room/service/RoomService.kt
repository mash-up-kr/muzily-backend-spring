package kr.mashup.ladder.room.service

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomNotFoundException
import kr.mashup.ladder.domain.room.domain.RoomRepository
import kr.mashup.ladder.domain.room.dto.RoomDto
import kr.mashup.ladder.room.dto.RoomChatResponse
import kr.mashup.ladder.room.dto.RoomCreateRequest
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val simpMessagingTemplate: SimpMessagingTemplate,
) {
    @Transactional
    fun create(request: RoomCreateRequest): RoomDto {
        val room = roomRepository.save(Room(description = request.description))
        return RoomDto.from(room)
    }

    @Transactional(readOnly = true)
    fun findBy(roomId: Long): RoomDto {
        val room = roomRepository.findById(roomId) ?: throw RoomNotFoundException("$roomId")
        return RoomDto.from(room)
    }

    fun publishChat(roomId: Long, chat: String) {
        val payload = WsResponse(WsResponseType.CHAT, RoomChatResponse(chat))
        simpMessagingTemplate.convertAndSend("/sub/rooms/${roomId}", payload)
    }
}
