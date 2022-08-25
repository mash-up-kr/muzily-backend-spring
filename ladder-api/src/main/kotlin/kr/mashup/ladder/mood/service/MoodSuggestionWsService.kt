package kr.mashup.ladder.mood.service

import kr.mashup.ladder.domain.mood.infra.jpa.MoodSuggestionRepository
import kr.mashup.ladder.domain.room.domain.RoomMessage
import kr.mashup.ladder.domain.room.domain.RoomMessagePublisher
import kr.mashup.ladder.domain.room.domain.RoomMessageType
import kr.mashup.ladder.domain.room.domain.RoomTopic
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.mood.dto.request.SuggestMoodRequest
import kr.mashup.ladder.room.service.RoomServiceHelper.validateIsParticipant
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MoodSuggestionWsService(
    private val moodSuggestionRepository: MoodSuggestionRepository,
    private val roomMessagePublisher: RoomMessagePublisher,
    private val roomRepository: RoomRepository,
) {

    @Transactional
    fun suggestRoomMood(roomId: Long, request: SuggestMoodRequest, memberId: Long) {
        validateIsParticipant(roomRepository = roomRepository, roomId = roomId, memberId = memberId)
        moodSuggestionRepository.save(request.toEntity(roomId = roomId, memberId = memberId))
        roomMessagePublisher.publish(
            roomTopic = RoomTopic(roomId),
            roomMessage = RoomMessage(
                type = RoomMessageType.MOOD_CHANGE_REQUEST,
                data = request.toMessage(roomId = roomId)
            )
        )
    }

}
