package kr.mashup.ladder.common.dto.response

data class PagingResponse<T>(
    val contents: List<T>,
    val totalCounts: Long,
    val cursor: CursorResponse,
) {

    companion object {
        fun <T> of(contents: List<T>, cursor: CursorResponse, totalCounts: Long): PagingResponse<T> {
            return PagingResponse(
                contents = contents,
                cursor = cursor,
                totalCounts = totalCounts,
            )
        }
    }

}


data class CursorResponse(
    val next: Long?,
    val hasNext: Boolean,
)
