package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.common.dto.response.BaseTimeResponse
import kr.mashup.ladder.domain.playlist.domain.Playlist
import kr.mashup.ladder.domain.room.domain.Room

data class RoomDetailInfoResponse(
    val roomId: Long,
    val description: String,
    val invitationKey: String,
    val participantsCount: Long = 0L, // TODO: 참여자 카운트 조회
    val moods: List<String>,
    val playlistId: Long? = null,
) : BaseTimeResponse() {

    companion object {
        fun of(room: Room, playlistId: Long): RoomDetailInfoResponse {
            val response = RoomDetailInfoResponse(
                roomId = room.id,
                description = room.description,
                invitationKey = room.invitationKey.invitationKey,
                moods = room.moods.map { mood -> mood.name },
                playlistId = playlistId,
            )
            response.setBaseTime(room)
            return response
        }

        fun of(rooms: List<Room>, playlists: List<Playlist>): List<RoomDetailInfoResponse> {
            val playlistByRoomId = playlists.associateBy { it.roomId }
            return rooms.map {
                val dto = RoomDetailInfoResponse(
                    roomId = it.id,
                    description = it.description,
                    invitationKey = it.invitationKey.invitationKey,
                    moods = it.moods.map { mood -> mood.name },
                    playlistId = playlistByRoomId[it.id]?.id,
                )
                dto.setBaseTime(it)
                dto
            }
        }
    }

}
