package kr.mashup.ladder.common.dto.payload

data class WsResponsePayload<T>(
    val data: T?,
) {
    companion object {
        fun <T> ok(data: T): WsResponsePayload<T> {
            return WsResponsePayload(
                data = data
            )
        }
    }
}
