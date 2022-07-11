package kr.mashup.ladder.playlistitem.controller

import io.swagger.annotations.ApiOperation
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.playlistitem.dto.PlaylistItemDto
import kr.mashup.ladder.playlistitem.service.PlaylistItemService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaylistItemApiController(
    private val playlistItemService: PlaylistItemService,
) {
    @ApiOperation("계류중인 재생목록 항목들을 조회합니다")
    @Auth(allowedAnonymous = false)
    @GetMapping("/api/v1/playlists/{playlistId}/pending-items")
    fun findPending(
        @PathVariable playlistId: Long,
        @MemberId memberId: Long,
    ): List<PlaylistItemDto> {
        return playlistItemService.findPending(playlistId, memberId)
    }
}
