package kr.mashup.ladder.room.controller

import kr.mashup.ladder.common.dto.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RoomApiController {

    @GetMapping("/v1/rooms")
    fun getRooms(): ApiResponse<String> {
        return ApiResponse.OK
    }
}
