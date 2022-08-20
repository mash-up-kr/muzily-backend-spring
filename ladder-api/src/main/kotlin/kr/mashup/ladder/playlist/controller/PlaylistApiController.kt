package kr.mashup.ladder.playlist.controller

import io.swagger.annotations.ApiOperation
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.playlist.dto.PlaylistDto
import kr.mashup.ladder.playlist.dto.PlaylistItemDto
import kr.mashup.ladder.playlist.service.PlaylistService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaylistApiController(
    private val playlistService: PlaylistService,
) {
    @ApiOperation("재생목록을 조회합니다")
    @Auth
    @GetMapping("/api/v1/playlists/{playlistId}")
    fun findById(
        @PathVariable playlistId: Long,
        @MemberId memberId: Long,
    ): PlaylistDto {
        return playlistService.findById(playlistId = playlistId, memberId = memberId)
    }

    @ApiOperation("계류중인 재생목록 항목들을 조회합니다")
    @Auth(allowedAnonymous = false)
    @GetMapping("/api/v1/playlists/{playlistId}/pending-items")
    fun findPendingItems(
        @PathVariable playlistId: Long,
        @MemberId memberId: Long,
    ): List<PlaylistItemDto> {
        return playlistService.findPendingItems(playlistId = playlistId, memberId = memberId)
    }
}
