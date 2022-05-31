package kr.mashup.ladder.domain.common.error

/**
 * 에러 코드
 * https://github.com/mash-up-kr/ladder-server/wiki/004.-%EC%97%90%EB%9F%AC-%EC%BD%94%EB%93%9C
 */

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
    val shouldLog: Boolean,
) {
    // common
    INVALID_REQUEST(status = 400, code = "C001", message = "잘못된 요청입니다", shouldLog = false),
    UNAUTHORIZED(status = 401, code = "C002", message = "인증에 실패하였습니다 다시 로그인 해주세요", shouldLog = false),
    FORBIDDEN(status = 403, code = "C003", message = "해당 권한이 존재하지 않습니다", shouldLog = false),
    NOT_FOUND(status = 404, code = "C004", message = "해당하는 리소스는 존재하지 않습니다", shouldLog = false),
    CONFLICT(status = 409, code = "C005", message = "중복된 리소스가 존재합니다", shouldLog = false),
    UNKNOWN_ERROR(status = 500, code = "C006", message = "서버에서 에러가 발생하였습니다 ㅠㅠ", shouldLog = true),

    // room
    ROOM_NOT_FOUND(status = 404, code = "R001", message = "해당하는 방이 존재하지 않습니다", shouldLog = false),

    // ...
}
