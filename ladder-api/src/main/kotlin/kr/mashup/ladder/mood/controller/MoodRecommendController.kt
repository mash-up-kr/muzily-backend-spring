package kr.mashup.ladder.mood.controller

import io.swagger.annotations.ApiOperation
import kr.mashup.ladder.common.dto.request.CursorPagingRequest
import kr.mashup.ladder.common.dto.response.PagingResponse
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.domain.common.constants.ApiResponseConstants.SUCCESS
import kr.mashup.ladder.mood.dto.request.AddMoodRecommendRequest
import kr.mashup.ladder.mood.dto.response.MoodRecommendResponse
import kr.mashup.ladder.mood.service.MoodRecommendService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class MoodRecommendController(
    private val moodRecommendService: MoodRecommendService,
) {

    @ApiOperation("방의 참가자가 방에 분위기를 추천합니다")
    @Auth
    @PostMapping("/api/v1/room/{roomId}/mood/recommends")
    fun recommendMoodOfRoom(
        @PathVariable roomId: Long,
        @MemberId memberId: Long,
        @Valid @RequestBody request: AddMoodRecommendRequest,
    ): String {
        moodRecommendService.addMoodRecommend(roomId = roomId, memberId = memberId, request = request)
        return SUCCESS
    }

    @ApiOperation("관리자가 방에 추천된 분위기 목록을 조회합니다 (페이징)")
    @Auth(allowedAnonymous = false)
    @GetMapping("/api/v1/room/{roomId}/mood/recommends")
    fun retrieveMoods(
        @PathVariable roomId: Long,
        @MemberId memberId: Long,
        @Valid request: CursorPagingRequest,
    ): PagingResponse<MoodRecommendResponse> {
        return moodRecommendService.retrieveMoodRecommends(
            roomId = roomId,
            memberId = memberId,
            request = request,
        )
    }

    @ApiOperation("관리자가 방에 추천된 분위기를 삭제합니다")
    @Auth(allowedAnonymous = false)
    @DeleteMapping("/api/v1/room/{roomId}/mood/recommends/{recommendId}")
    fun readRoomMoodRecommend(
        @PathVariable roomId: Long,
        @MemberId memberId: Long,
        @PathVariable recommendId: Long,
    ): String {
        moodRecommendService.deleteMoodRecommend(
            roomId = roomId,
            memberId = memberId,
            recommendId = recommendId
        )
        return SUCCESS
    }

}
