package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.room.domain.RoomMood
import kr.mashup.ladder.domain.room.infra.jpa.RoomMoodRepository
import kr.mashup.ladder.room.dto.request.RoomMoodRequest
import kr.mashup.ladder.room.dto.response.RoomMoodResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomMoodService(
    private val roomMoodRepository: RoomMoodRepository,
) {

    @Transactional
    fun addRoomMoods(roomId: Long, requests: Set<RoomMoodRequest>) {
        val moods = requests.map { mood -> mood.toEntity(roomId = roomId) }
        roomMoodRepository.saveAll(moods)
    }

    @Transactional
    fun updateRoomMoods(roomId: Long, requests: Set<RoomMoodRequest>) {
        // TODO: 변경 필요
        val roomMoods = roomMoodRepository.findAllByRoomId(roomId)
        roomMoodRepository.deleteAll(roomMoods)
        val moods = requests.map { mood -> mood.toEntity(roomId = roomId) }
        roomMoodRepository.saveAll(moods)
    }

    @Transactional(readOnly = true)
    fun getRoomMoods(roomId: Long): List<RoomMood> {
        return roomMoodRepository.findAllByRoomId(roomId)
    }

    @Transactional(readOnly = true)
    fun getRoomMoodsMap(roomIds: List<Long>): Map<Long, List<RoomMood>> {
        return roomMoodRepository.findAllByInRoomIds(roomIds)
            .groupBy { mood -> mood.roomId }
    }

}
