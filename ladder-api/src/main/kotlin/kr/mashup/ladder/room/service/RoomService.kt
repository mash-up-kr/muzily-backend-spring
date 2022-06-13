package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomRepository
import kr.mashup.ladder.domain.room.dto.RoomDto
import kr.mashup.ladder.room.dto.RoomCreateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RoomService(
    private val roomRepository: RoomRepository,
) {
    @Transactional
    fun create(request: RoomCreateRequest): RoomDto {
        val room = roomRepository.save(Room(description = request.description))
        return RoomDto.from(room)
    }

    fun findBy(id: Long): RoomDto {
        val room = roomRepository.findById(id).orElseThrow { NoSuchElementException(id.toString()) }
        return RoomDto.from(room)
    }
}
