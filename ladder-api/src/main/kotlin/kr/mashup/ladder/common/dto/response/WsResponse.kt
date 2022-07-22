package kr.mashup.ladder.common.dto.response

import kr.mashup.ladder.domain.common.exception.ErrorCode

data class WsResponse<T>(
    val type: WsResponseType,
    val code: String = "",
    val message: String = "",
    val data: T?,
) {
    companion object {
        fun <T> ok(type: WsResponseType, data: T): WsResponse<T> {
            return WsResponse(
                type = type,
                data = data
            )
        }

        fun error(errorCode: ErrorCode): WsResponse<Nothing> {
            return WsResponse(
                code = errorCode.code,
                type = WsResponseType.ERROR,
                message = errorCode.message,
                data = null
            )
        }
    }
}

