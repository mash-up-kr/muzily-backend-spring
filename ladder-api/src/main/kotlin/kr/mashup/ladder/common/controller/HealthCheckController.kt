package kr.mashup.ladder.common.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/api/health")
    fun health(): String {
        return "OK"
    }

}
