package kr.mashup.ladder.common.advice

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_QUEUE
import kr.mashup.ladder.common.exception.ErrorCode
import kr.mashup.ladder.common.exception.model.LadderBaseException
import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.web.bind.annotation.ControllerAdvice

private val logger = KotlinLogging.logger {}

@ControllerAdvice
class WsControllerExceptionAdvice {
    @MessageExceptionHandler(LadderBaseException::class)
    @SendToUser(destinations = [WS_DESTINATION_PREFIX_QUEUE])
    private fun handleBaseException(exception: LadderBaseException): WsResponse<Nothing> {
        logging(exception)
        return WsResponse.error(exception.errorCode)
    }

    private fun logging(exception: LadderBaseException) {
        if (exception.errorCode.shouldLog) {
            logger.error(exception.message, exception)
            return
        }
        logger.warn(exception.message, exception)
    }

    @MessageExceptionHandler(Exception::class)
    @SendToUser(destinations = [WS_DESTINATION_PREFIX_QUEUE])
    private fun handleInternalServerException(exception: Exception): WsResponse<Nothing> {
        logger.error(exception.message, exception)
        return WsResponse.error(ErrorCode.UNKNOWN_ERROR)
    }
}
