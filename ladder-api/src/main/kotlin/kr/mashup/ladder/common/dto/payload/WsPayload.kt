package kr.mashup.ladder.common.dto.payload

data class WsPayload<T>(
    val data: T?,
) {
    companion object {
        fun <T> ok(data: T): WsPayload<T> {
            return WsPayload(
                data = data
            )
        }
    }
}
