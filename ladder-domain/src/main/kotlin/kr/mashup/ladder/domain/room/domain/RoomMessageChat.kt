package kr.mashup.ladder.domain.room.domain

data class RoomMessageChat(
    val roomId: Long,
    val chat: String,
)


data class RoomSubscribe(
    val roomId: Long,
    val memberId: Long,
)
