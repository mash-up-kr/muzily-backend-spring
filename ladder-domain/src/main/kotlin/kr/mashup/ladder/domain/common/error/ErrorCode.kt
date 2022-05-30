package kr.mashup.ladder.domain.common.error

/**
 * ErrorCode (v2022.05.30)
 * 400: 잘못된 요청 + 필수 파라미터 안 넘겼을 때
 * 401: 인증 실패
 * 403: 권한이 없는 경우
 * 404: NotFound 리소스
 * 409: 중복된 리소스
 * 500: 서버 에러
 */

enum class ErrorCode(
    val code: String,
    val status: Int, // TODO: HttpStatus 로 변환 고려
    val shouldLog: Boolean,
) {
    // common
    INVALID_REQUEST("C001", 400, false),
    UNAUTHORIZED("C002", 401, false),
    FORBIDDEN("C003", 403, false),
    NOT_FOUND("C004", 404, false),
    CONFLICT("C005", 409, false),
    UNKNOWN_ERROR("C006", 500, true),

    // room
    ROOM_NOT_FOUND("R001", 404, false),

    // ...
}
