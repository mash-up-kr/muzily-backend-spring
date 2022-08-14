package kr.mashup.ladder.domain.common.domain

data class CursorSupporter<T>(
    private val itemsWithNextCursor: List<T>,
    val cursorSize: Long,
) {

    fun getItems(): List<T> {
        if (hasNext()) {
            return itemsWithNextCursor.subList(0, cursorSize.toInt())
        }
        return itemsWithNextCursor
    }

    fun hasNext(): Boolean {
        return itemsWithNextCursor.size > cursorSize
    }

    fun nextCursor(): T? {
        if (hasNext()) {
            return itemsWithNextCursor[cursorSize.toInt() - 1]
        }
        return null
    }

    companion object {
        fun <T> of(itemsWithNextCursor: List<T>, cursorSize: Long): CursorSupporter<T> {
            return CursorSupporter(
                itemsWithNextCursor = itemsWithNextCursor,
                cursorSize = cursorSize
            )
        }
    }

}
