package kr.mashup.ladder.mood.service

import kr.mashup.ladder.common.dto.request.CursorPagingRequest
import kr.mashup.ladder.common.dto.response.CursorResponse
import kr.mashup.ladder.common.dto.response.PagingResponse
import kr.mashup.ladder.domain.mood.exception.MoodSuggestionNotFoundException
import kr.mashup.ladder.domain.mood.infra.jpa.MoodSuggestionRepository
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.mood.dto.response.MoodSuggestionResponse
import kr.mashup.ladder.room.service.RoomServiceHelper.validateIsCreator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MoodSuggestionService(
    private val moodSuggestionRepository: MoodSuggestionRepository,
    private val roomRepository: RoomRepository,
) {

    @Transactional
    fun retrieveMoodSuggestions(
        roomId: Long,
        request: CursorPagingRequest,
        memberId: Long,
    ): PagingResponse<MoodSuggestionResponse> {
        validateIsCreator(roomRepository = roomRepository, roomId = roomId, memberId = memberId)

        val suggestionsCursor = moodSuggestionRepository.findAllByRoomIdLessThanCursorWithLimit(
            roomId = roomId,
            cursor = request.cursor,
            size = request.size,
        )

        return PagingResponse.of(
            cursor = CursorResponse(
                next = suggestionsCursor.nextCursor()?.id,
                hasNext = suggestionsCursor.hasNext(),
            ),
            contents = suggestionsCursor.getItems().map { suggestion -> MoodSuggestionResponse.of(suggestion) }
        )
    }

    @Transactional
    fun deleteMoodSuggestion(roomId: Long, memberId: Long, suggestionId: Long) {
        validateIsCreator(roomRepository = roomRepository, roomId = roomId, memberId = memberId)
        val suggestion = moodSuggestionRepository.findByIdAndRoomId(suggestionId, roomId)
            ?: throw MoodSuggestionNotFoundException("방($roomId)에 해당하는 분위기 변경 요청($suggestionId)는 존재하지 않습니다")
        moodSuggestionRepository.delete(suggestion)
    }

}
