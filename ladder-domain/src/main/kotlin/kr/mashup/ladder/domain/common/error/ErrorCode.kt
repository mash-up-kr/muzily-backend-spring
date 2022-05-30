package kr.mashup.ladder.domain.common.error

/**
 * 에러 코드
 * https://github.com/mash-up-kr/ladder-server/wiki/004.-%EC%97%90%EB%9F%AC-%EC%BD%94%EB%93%9C
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
