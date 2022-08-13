package kr.mashup.ladder.common.controller

import kr.mashup.ladder.domain.common.constants.ApiResponseConstants.SUCCESS
import kr.mashup.ladder.domain.common.exception.model.BadGatewayException
import org.springframework.boot.availability.ApplicationAvailability
import org.springframework.boot.availability.LivenessState
import org.springframework.boot.availability.ReadinessState
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(
    private val applicationAvailability: ApplicationAvailability,
) {

    @GetMapping("/health")
    fun health(): String {
        return SUCCESS
    }

    @GetMapping("/liveness")
    fun liveness(): String {
        val livenessState = applicationAvailability.livenessState
        if (livenessState != LivenessState.CORRECT) {
            throw BadGatewayException("Server is not in liveness")
        }
        return SUCCESS
    }

    @GetMapping("/readiness")
    fun readiness(): String {
        val readinessState = applicationAvailability.readinessState
        if (readinessState != ReadinessState.ACCEPTING_TRAFFIC) {
            throw BadGatewayException("Server is not in readiness")
        }
        return SUCCESS
    }

}
