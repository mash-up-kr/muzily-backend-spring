package kr.mashup.ladder.mood.service

import kr.mashup.ladder.domain.mood.domain.Mood
import kr.mashup.ladder.domain.mood.infra.jpa.MoodRepository
import kr.mashup.ladder.room.dto.request.RoomMoodRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MoodService(
    private val moodRepository: MoodRepository,
) {

    @Transactional
    fun addRoomMoods(roomId: Long, requests: Set<RoomMoodRequest>) {
        val moods = requests.map { mood -> mood.toEntity(roomId = roomId) }
        moodRepository.saveAll(moods)
    }

    @Transactional
    fun updateRoomMoods(roomId: Long, requests: Set<RoomMoodRequest>) {
        // TODO: 변경 필요
        val roomMoods = moodRepository.findAllByRoomId(roomId)
        moodRepository.deleteAll(roomMoods)
        val moods = requests.map { mood -> mood.toEntity(roomId = roomId) }
        moodRepository.saveAll(moods)
    }

    @Transactional(readOnly = true)
    fun getRoomMoods(roomId: Long): List<Mood> {
        return moodRepository.findAllByRoomId(roomId)
    }

}
