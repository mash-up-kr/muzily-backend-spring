package kr.mashup.ladder.common.dto.response

data class WsResponse<T>(
    val type: WsResponseType,
    val data: T,
)

