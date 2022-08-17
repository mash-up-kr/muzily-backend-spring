package kr.mashup.ladder.common.dto.response

data class PagingResponse<T>(
    val contents: List<T>,
    val cursor: CursorResponse,
) {

    companion object {
        fun <T> of(contents: List<T>, cursor: CursorResponse): PagingResponse<T> {
            return PagingResponse(
                contents = contents,
                cursor = cursor
            )
        }
    }

}


data class CursorResponse(
    val next: Long?,
    val hasNext: Boolean,
)
