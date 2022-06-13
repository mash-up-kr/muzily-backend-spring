package kr.mashup.ladder.room.service

import kr.mashup.ladder.common.dto.payload.WsPayload
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomRepository
import kr.mashup.ladder.domain.room.dto.RoomDto
import kr.mashup.ladder.room.dto.RoomChatMessage
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
    fun findBy(id: Long): RoomDto {
        val room = roomRepository.findById(id).orElseThrow { NoSuchElementException(id.toString()) }
        return RoomDto.from(room)
    }

    fun publishChat(id: Long, message: RoomChatMessage) {
        val payload = WsPayload.ok(message.chat)
        simpMessagingTemplate.convertAndSend("/sub/rooms/${id}", payload)
    }
}
