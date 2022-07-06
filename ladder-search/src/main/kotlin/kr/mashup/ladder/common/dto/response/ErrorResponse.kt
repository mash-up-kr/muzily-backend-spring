package kr.mashup.ladder.common.dto.response

import kr.mashup.ladder.domain.common.error.ErrorCode

data class ErrorResponse<T>(
    val code: String = "",
    val message: String = "",
    val data: T? = null,
) {

    companion object {

        fun of(errorCode: ErrorCode): ErrorResponse<Nothing> {
            return ErrorResponse(
                code = errorCode.code,
                message = errorCode.message,
            )
        }

        fun of(errorCode: ErrorCode, message: String): ErrorResponse<Nothing> {
            return ErrorResponse(
                code = errorCode.code,
                message = message,
            )
        }
    }

}
