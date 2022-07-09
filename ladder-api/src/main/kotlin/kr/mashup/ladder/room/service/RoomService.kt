package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.room.domain.*
import kr.mashup.ladder.domain.room.domain.chat.RoomChatMessage
import kr.mashup.ladder.domain.room.dto.RoomDto
import kr.mashup.ladder.room.dto.request.RoomCreateRequest
import kr.mashup.ladder.room.dto.request.RoomSendChatRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
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

    fun sendChat(roomId: Long, request: RoomSendChatRequest) {
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.CHAT, request.toMessage(roomId))
        )
    }

    fun sendEmoji(roomId: Long, request: RoomSendEmojiRequest) {
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.EMOJI, request.toMessage(roomId))
        )
    }
}
