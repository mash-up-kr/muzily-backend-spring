package kr.mashup.ladder.mood.controller

import io.swagger.annotations.ApiOperation
import kr.mashup.ladder.common.constants.ApiResponseConstants.SUCCESS
import kr.mashup.ladder.common.dto.request.CursorPagingRequest
import kr.mashup.ladder.common.dto.response.PagingResponse
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.mood.dto.request.SuggestMoodRequest
import kr.mashup.ladder.mood.dto.response.MoodSuggestionResponse
import kr.mashup.ladder.mood.service.MoodSuggestionService
import kr.mashup.ladder.mood.service.MoodSuggestionWsService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class MoodSuggestionApiController(
    private val moodSuggestionWsService: MoodSuggestionWsService,
    private val moodSuggestionService: MoodSuggestionService,
) {

    @ApiOperation("방의 참가자가 방의 분위기 변경을 요청합니다 (웹 소켓 연동 필요)")
    @Auth
    @PostMapping("/api/v1/room/{roomId}/mood/suggestions")
    fun suggestRoomMood(
        @PathVariable roomId: Long,
        @MemberId memberId: Long,
        @Valid @RequestBody request: SuggestMoodRequest,
    ): String {
        moodSuggestionWsService.suggestRoomMood(roomId = roomId, request = request, memberId = memberId)
        return SUCCESS
    }

    @ApiOperation("방장이 방에 제안된 분위기 목록을 조회합니다 (최근꺼부터 커서 페이징)")
    @Auth
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

    @ApiOperation("방장이 방에 요청된 분위기를 읽음 처리 합니다")
    @Auth
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
