package kr.mashup.ladder.room

import kr.mashup.ladder.room.dto.request.RoomAddPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomCreateRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest

class RoomFixture {
    companion object {
        fun `방 생성 요청값`(): RoomCreateRequest {
            return RoomCreateRequest(
                description = "매쇼~쉬는탐"
            )
        }

        fun `방 재생목록 항목 신청 요청값`(playlistId: Long): RoomSendPlaylistItemRequestRequest {
            return RoomSendPlaylistItemRequestRequest(
                playlistId = playlistId,
                videoId = "LqfimuFAFJ8",
                title = "라일락",
                duration = 229,
                thumbnail = "https://i.ytimg.com/vi/LqfimuFAFJ8/maxresdefault.jpg"
            )
        }

        fun `방 재생목록 항목 추가 요청값`(playlistId: Long): RoomAddPlaylistItemRequest {
            return RoomAddPlaylistItemRequest(
                playlistId = playlistId,
                videoId = "LqfimuFAFJ8",
                title = "라일락",
                duration = 229,
                thumbnail = "https://i.ytimg.com/vi/LqfimuFAFJ8/maxresdefault.jpg"
            )
        }
    }
}
