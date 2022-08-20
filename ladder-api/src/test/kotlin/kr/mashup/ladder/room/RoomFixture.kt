package kr.mashup.ladder.room

import kr.mashup.ladder.domain.room.domain.emoji.EmojiType
import kr.mashup.ladder.room.dto.request.RoomAcceptPlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomAddPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomCreateRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest

class RoomFixture {
    companion object {
        fun `방 생성 요청값`(): RoomCreateRequest {
            return RoomCreateRequest(
                name = "매쇼~쉬는탐",
                emojiType = EmojiType.BOOK,
            )
        }

        fun `방 100% 하트 이모지 보내기 요청값`(): RoomSendEmojiRequest {
            return RoomSendEmojiRequest(EmojiType.HEART, 100, "Hello, World!")
        }

        fun `방 재생목록 항목 신청 요청값`(playlistId: Long): RoomSendPlaylistItemRequestRequest {
            return RoomSendPlaylistItemRequestRequest(
                playlistId = playlistId,
                videoId = "LqfimuFAFJ8",
                title = "라일락",
                duration = 229,
                thumbnail = "https://i.ytimg.com/vi/LqfimuFAFJ8/maxresdefault.jpg",
                dominantColor = "#FFFFFF",
            )
        }

        fun `방 재생목록 항목 추가 요청값`(playlistId: Long): RoomAddPlaylistItemRequest {
            return RoomAddPlaylistItemRequest(
                playlistId = playlistId,
                videoId = "LqfimuFAFJ8",
                title = "라일락",
                duration = 229,
                thumbnail = "https://i.ytimg.com/vi/LqfimuFAFJ8/maxresdefault.jpg",
                dominantColor = "#FFFFFF",
            )
        }

        fun `방 재생목록 항목 신청 승인 요청값`(playlistId: Long, playlistItemId: Long): RoomAcceptPlaylistItemRequestRequest {
            return RoomAcceptPlaylistItemRequestRequest(
                playlistId = playlistId,
                playlistItemId = playlistItemId,
            )
        }
    }
}
