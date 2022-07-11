package kr.mashup.ladder.playlist.controller

import io.swagger.annotations.ApiOperation
import kr.mashup.ladder.playlist.dto.PlaylistDto
import kr.mashup.ladder.playlist.service.PlaylistService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaylistApiController(
    private val playlistService: PlaylistService,
) {
    @ApiOperation("재생목록을 조회합니다")
    @GetMapping("/api/v1/playlists/{playlistId}")
    fun findById(@PathVariable playlistId: Long): PlaylistDto {
        return playlistService.getPlaylist(playlistId)
    }
}
