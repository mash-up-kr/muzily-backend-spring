package kr.mashup.ladder.common.exception

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
    NOT_FOUND(status = 404, code = "C005", message = "해당하는 리소스는 존재하지 않습니다", shouldLog = false),
    METHOD_NOT_ALLOWED(status = 405, code = "C006", message = "Method Not Allowed", shouldLog = false),
    CONFLICT(status = 409, code = "C007", message = "중복된 리소스가 존재합니다", shouldLog = false),
    UNKNOWN_ERROR(status = 500, code = "C008", message = "서버에서 에러가 발생하였습니다 ㅠㅠ", shouldLog = true),

    // auth
    INVALID_AUTH_CODE(status = 400, code = "AT001", message = "잘못된 인증 코드입니다", shouldLog = false),
    FORBIDDEN_NOT_ALLOWED_ANONYMOUS(status = 403, code = "AT002", message = "게스트 사용자는 접근할 수 없습니다", shouldLog = false),

    // member
    MEMBER_NOT_FOUND(status = 404, code = "A001", message = "해당하는 계정은 존재하지 않습니다", shouldLog = false),

    // room
    ROOM_NOT_FOUND(status = 404, code = "R001", message = "해당하는 방은 존재하지 않습니다", shouldLog = false),
    ALREADY_ROOM_CREATOR_CONFLICT(
        status = 409,
        code = "R002",
        message = "이미 해당 계정에서 생성한 방이 존재합니다.\n1개의 방만 생성할 수 있습니다",
        shouldLog = false
    ),
    ALREADY_EXISTS_MEMBER_IN_ROOM_CONFLICT(
        status = 409,
        code = "R003",
        message = "해당하는 방에 이미 참여하고 있습니다",
        shouldLog = false
    ),
    CREATED_ROOM_NOT_FOUND(status = 404, code = "R004", message = "해당하는 멤버가 생성한 방이 존재하지 않습니다", shouldLog = false),
    IS_NOT_CREATOR_IN_ROOM_FORBIDDEN(status = 403, code = "R005", message = "방장만이 접근할 수 있습니다", shouldLog = false),

    // playlist
    PLAYLIST_NOT_FOUND(status = 404, code = "P001", message = "해당하는 재생목록이 존재하지 않습니다", shouldLog = false),

    // playlist item
    PLAYLIST_ITEM_NOT_FOUND(status = 404, code = "PI001", message = "해당하는 재생목록 항목이 존재하지 않습니다", shouldLog = false),

    // youtube
    YOUTUBE_VIDEO_NOT_FOUND(status = 404, code = "YO001", message = "해당하는 유튜브 영상은 존재하지 않습니다", shouldLog = false),
    YOUTUBE_VIDEO_NOT_ALLOWED_CATEGORY(
        status = 403,
        code = "Y002",
        message = "해당 영상은 허용되지 않은 카테고리 입니다",
        shouldLog = false
    ),

    // Mood Suggestion
    MOOD_SUGGESTION_NOT_FOUND(status = 404, code = "M001", message = "해당하는 분위기 제안 정보는 존재하지 않습니다", shouldLog = false),
}
