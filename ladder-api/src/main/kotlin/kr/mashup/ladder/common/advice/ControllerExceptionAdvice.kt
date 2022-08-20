package kr.mashup.ladder.common.advice

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import kr.mashup.ladder.common.dto.response.ErrorResponse
import kr.mashup.ladder.common.exception.ErrorCode
import kr.mashup.ladder.common.exception.model.LadderBaseException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.stream.Collectors

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ControllerExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    private fun handleBadRequest(e: BindException): ErrorResponse<Nothing> {
        val errorMessage = e.bindingResult.fieldErrors.stream()
            .map { fieldError -> fieldError.defaultMessage + " [${fieldError.field}]" }
            .collect(Collectors.joining("\n"))
        logger.warn(errorMessage, e)
        return ErrorResponse.of(ErrorCode.INVALID_REQUEST, errorMessage)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    private fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ErrorResponse<Nothing> {
        logger.warn(e.message)
        if (e.rootCause is MissingKotlinParameterException) {
            val parameterName = (e.rootCause as MissingKotlinParameterException).parameter.name
            return ErrorResponse.of(ErrorCode.INVALID_REQUEST, "파라미터 ($parameterName)을 입력해주세요")
        }
        return ErrorResponse.of(ErrorCode.INVALID_REQUEST)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    private fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ErrorResponse<Nothing> {
        logger.warn(e.message)
        return ErrorResponse.of(ErrorCode.INVALID_REQUEST)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException::class)
    private fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ErrorResponse<Nothing> {
        logger.warn(e.message, e)
        return ErrorResponse.of(ErrorCode.INVALID_REQUEST, "파라미터 (${e.parameterName})을 입력해주세요")
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    private fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ErrorResponse<Nothing> {
        logger.warn(e.message, e)
        return ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(LadderBaseException::class)
    private fun handleBaseException(exception: LadderBaseException): ResponseEntity<ErrorResponse<Nothing>> {
        logging(exception)
        return ResponseEntity.status(exception.errorCode.status)
            .body(ErrorResponse.of(exception.errorCode))
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
    private fun handleInternalServerException(exception: Exception): ErrorResponse<Nothing> {
        logger.error(exception.message, exception)
        return ErrorResponse.of(ErrorCode.UNKNOWN_ERROR)
    }

}
