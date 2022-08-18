package kr.mashup.ladder.mood.service

import kr.mashup.ladder.common.dto.request.CursorPagingRequest
import kr.mashup.ladder.common.dto.response.CursorResponse
import kr.mashup.ladder.common.dto.response.PagingResponse
import kr.mashup.ladder.domain.common.exception.model.ForbiddenException
import kr.mashup.ladder.domain.mood.exception.MoodRecommendNotFoundException
import kr.mashup.ladder.domain.mood.infra.jpa.MoodRecommendRepository
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.mood.dto.request.AddMoodRecommendRequest
import kr.mashup.ladder.mood.dto.response.MoodRecommendResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MoodRecommendService(
    private val moodRecommendRepository: MoodRecommendRepository,
    private val roomRepository: RoomRepository,
) {

    @Transactional
    fun addMoodRecommend(roomId: Long, request: AddMoodRecommendRequest, memberId: Long) {
        moodRecommendRepository.saveAll(request.toEntity(roomId = roomId, memberId = memberId))
    }

    @Transactional
    fun retrieveMoodRecommends(
        roomId: Long,
        request: CursorPagingRequest,
        memberId: Long,
    ): PagingResponse<MoodRecommendResponse> {
        validateHasAuthority(roomId = roomId, creatorId = memberId)

        val recommendCursor = moodRecommendRepository.findAllByRoomIdLessThanCursorWithLimit(
            roomId = roomId,
            cursor = request.cursor,
            size = request.size,
        )

        return PagingResponse.of(
            cursor = CursorResponse(
                next = recommendCursor.nextCursor()?.id,
                hasNext = recommendCursor.hasNext(),
            ),
            contents = recommendCursor.getItems().map { recommend -> MoodRecommendResponse.of(recommend) }
        )
    }

    @Transactional
    fun deleteMoodRecommend(roomId: Long, memberId: Long, recommendId: Long) {
        validateHasAuthority(roomId = roomId, creatorId = memberId)
        val recommend = moodRecommendRepository.findByIdAndRoomId(recommendId, roomId)
            ?: throw MoodRecommendNotFoundException("방($roomId)에 해당하는 추천 분위기($recommendId)는 존재하지 않습니다")
        moodRecommendRepository.delete(recommend)
    }

    private fun validateHasAuthority(roomId: Long, creatorId: Long) {
        if (!roomRepository.existsRoomByIdAndCreatorId(roomId = roomId, creatorId = creatorId)) {
            throw ForbiddenException("멤버($creatorId)는 해당 방($roomId)에 대한 권한이 없습니다")
        }
    }

}
