package kr.mashup.ladder.common.advice

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import kr.mashup.ladder.common.dto.response.ApiResponse
import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.LadderBaseException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ControllerExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    private fun handleBadRequest(e: BindException): ApiResponse<Nothing> {
        val errorMessage = e.bindingResult.fieldErrors.stream()
            .map { fieldError -> fieldError.defaultMessage + " [${fieldError.field}]" }
            .collect(Collectors.joining("\n"))
        logger.warn(errorMessage, e)
        return ApiResponse.error(ErrorCode.INVALID_REQUEST, errorMessage)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    private fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ApiResponse<Nothing> {
        logger.warn(e.message)
        if (e.rootCause is MissingKotlinParameterException) {
            val parameterName = (e.rootCause as MissingKotlinParameterException).parameter.name
            return ApiResponse.error(ErrorCode.INVALID_REQUEST, "파라미터 ($parameterName)을 입력해주세요")
        }
        return ApiResponse.error(ErrorCode.INVALID_REQUEST)
    }

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
