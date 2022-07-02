package kr.mashup.ladder.common.dto.response

import kr.mashup.ladder.domain.common.error.ErrorCode

data class ApiResponse<T>(
    val code: String = "",
    val message: String = "",
    val data: T? = null,
) {

    companion object {
        val OK = ApiResponse(data = "OK")

        fun <T> ok(data: T): ApiResponse<T> {
            return ApiResponse(
                data = data
            )
        }

        fun error(errorCode: ErrorCode): ApiResponse<Nothing> {
            return ApiResponse(
                code = errorCode.code,
                message = errorCode.message,
            )
        }

        fun error(errorCode: ErrorCode, message: String): ApiResponse<Nothing> {
            return ApiResponse(
                code = errorCode.code,
                message = message,
            )
        }
    }

}
