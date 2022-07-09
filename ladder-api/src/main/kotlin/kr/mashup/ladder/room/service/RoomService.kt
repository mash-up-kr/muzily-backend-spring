package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomMessage
import kr.mashup.ladder.domain.room.domain.RoomMessageChat
import kr.mashup.ladder.domain.room.domain.RoomMessagePublisher
import kr.mashup.ladder.domain.room.domain.RoomMessageType
import kr.mashup.ladder.domain.room.domain.RoomNotFoundException
import kr.mashup.ladder.domain.room.domain.RoomRepository
import kr.mashup.ladder.domain.room.domain.RoomTopic
import kr.mashup.ladder.domain.room.dto.RoomDto
import kr.mashup.ladder.room.dto.request.RoomCreateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val roomMessagePublisher: RoomMessagePublisher,
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
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.CHAT, RoomMessageChat(roomId, chat)))
    }
}
