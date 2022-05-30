package kr.mashup.ladder.common.dto.response

data class ApiResponse<T>(
    val code: String = "",
    val message: String = "",
    val data: T?
) {

    companion object {
        val OK = ApiResponse(data = "OK")
        fun <T> ok(data: T): ApiResponse<T> {
            return ApiResponse(
                data = data
            )
        }
    }

}
