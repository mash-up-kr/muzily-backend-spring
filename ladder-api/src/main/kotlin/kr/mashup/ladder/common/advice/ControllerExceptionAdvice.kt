package kr.mashup.ladder.common.advice

import kr.mashup.ladder.common.dto.response.ApiResponse
import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.LadderBaseException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ControllerExceptionAdvice {

    @ExceptionHandler(LadderBaseException::class)
    private fun handleBaseException(exception: LadderBaseException): ResponseEntity<ApiResponse<Nothing>> {
        logging(exception)
        return ResponseEntity.status(exception.errorCode.status)
            .body(ApiResponse.error(exception.errorCode))
    }

    private fun logging(exception: LadderBaseException) {
        if (exception.errorCode.shouldLog) {
            logger.error(exception.message, exception)
            return
        }
        logger.warn(exception.message, exception)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    private fun handleInternalServerException(exception: Exception): ApiResponse<Nothing> {
        logger.error(exception.message, exception)
        return ApiResponse.error(ErrorCode.UNKNOWN_ERROR)
    }

}
