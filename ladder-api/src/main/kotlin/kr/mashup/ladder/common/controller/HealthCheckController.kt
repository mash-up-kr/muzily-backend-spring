package kr.mashup.ladder.common.controller

import kr.mashup.ladder.common.dto.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/api/health")
    fun health(): ApiResponse<String> {
        return ApiResponse.OK
    }

}
