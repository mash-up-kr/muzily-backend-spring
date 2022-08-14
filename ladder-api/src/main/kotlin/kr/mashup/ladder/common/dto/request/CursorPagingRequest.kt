package kr.mashup.ladder.common.dto.request

data class CursorPagingRequest(
    val cursor: Long?,
    val size: Long,
)
