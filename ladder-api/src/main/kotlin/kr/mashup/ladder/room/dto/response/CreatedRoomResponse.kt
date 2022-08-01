package kr.mashup.ladder.room.dto.response

data class CreatedRoomResponse(
    val roomId: Long,
) {

    companion object {
        fun of(roomId: Long): CreatedRoomResponse {
            return CreatedRoomResponse(
                roomId = roomId,
            )
        }
    }

}
