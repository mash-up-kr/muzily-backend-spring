package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomChat
import kr.mashup.ladder.domain.room.domain.RoomChatPublisher
import kr.mashup.ladder.domain.room.domain.RoomNotFoundException
import kr.mashup.ladder.domain.room.domain.RoomRepository
import kr.mashup.ladder.domain.room.dto.RoomDto
import kr.mashup.ladder.room.dto.RoomCreateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val roomChatPublisher: RoomChatPublisher,
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
        roomRepository.findById(roomId) ?: throw RoomNotFoundException("$roomId")
        roomChatPublisher.publish(RoomChat(roomId, chat))
    }
}
