package kr.mashup.ladder.mood.controller

import io.swagger.annotations.ApiOperation
import kr.mashup.ladder.common.dto.request.CursorPagingRequest
import kr.mashup.ladder.common.dto.response.PagingResponse
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.domain.common.constants.ApiResponseConstants.SUCCESS
import kr.mashup.ladder.mood.dto.request.AddMoodSuggestionRequest
import kr.mashup.ladder.mood.dto.response.MoodSuggestionResponse
import kr.mashup.ladder.mood.service.MoodSuggestionService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class MoodSuggestionController(
    private val moodSuggestionService: MoodSuggestionService,
) {

    @ApiOperation("방의 참가자가 방에 분위기를 제안합니다")
    @Auth
    @PostMapping("/api/v1/room/{roomId}/mood/suggestions")
    fun suggestRoomMood(
        @PathVariable roomId: Long,
        @MemberId memberId: Long,
        @Valid @RequestBody request: AddMoodSuggestionRequest,
    ): String {
        moodSuggestionService.suggestRoomMood(roomId = roomId, memberId = memberId, request = request)
        return SUCCESS
    }

    @ApiOperation("관리자가 방에 제안된 분위기 목록을 조회합니다 (페이징)")
    @Auth(allowedAnonymous = false)
    @GetMapping("/api/v1/room/{roomId}/mood/suggestions")
    fun retrieveMoodSuggestions(
        @PathVariable roomId: Long,
        @MemberId memberId: Long,
        @Valid request: CursorPagingRequest,
    ): PagingResponse<MoodSuggestionResponse> {
        return moodSuggestionService.retrieveMoodSuggestions(
            roomId = roomId,
            memberId = memberId,
            request = request,
        )
    }

    @ApiOperation("관리자가 방에 제안한 분위기를 삭제합니다")
    @Auth(allowedAnonymous = false)
    @DeleteMapping("/api/v1/room/{roomId}/mood/suggestions/{suggestionId}")
    fun deleteMoodSuggestion(
        @PathVariable roomId: Long,
        @MemberId memberId: Long,
        @PathVariable suggestionId: Long,
    ): String {
        moodSuggestionService.deleteMoodSuggestion(
            roomId = roomId,
            memberId = memberId,
            suggestionId = suggestionId
        )
        return SUCCESS
    }

}
