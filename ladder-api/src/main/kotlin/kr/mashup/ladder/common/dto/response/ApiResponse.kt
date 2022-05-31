package kr.mashup.ladder.common.dto.response

import kr.mashup.ladder.domain.common.error.ErrorCode

data class ApiResponse<T>(
    val code: String = "",
    val message: String = "", // TODO: message 서버에서 내려줄 건지 여부 확인 필요
    val data: T?,
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
                data = null
            )
        }
    }

}
