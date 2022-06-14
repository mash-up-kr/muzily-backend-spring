package kr.mashup.ladder.room.controller

import kr.mashup.ladder.common.dto.response.ApiResponse
import kr.mashup.ladder.domain.room.dto.RoomDto
import kr.mashup.ladder.room.dto.RoomCreateRequest
import kr.mashup.ladder.room.service.RoomService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rooms")
class RoomApiController(
    private val roomService: RoomService,
) {
    @PostMapping
    fun createRoom(@RequestBody request: RoomCreateRequest): ApiResponse<RoomDto> {
        val room = roomService.create(request)
        return ApiResponse.ok(room)
    }

    @GetMapping("/{roomId}")
    fun getRoom(@PathVariable roomId: Long): ApiResponse<RoomDto> {
        val room = roomService.findBy(roomId)
        return ApiResponse.ok(room)
    }
}
