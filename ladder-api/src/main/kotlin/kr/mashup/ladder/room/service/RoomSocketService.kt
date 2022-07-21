package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.room.domain.RoomMessage
import kr.mashup.ladder.domain.room.domain.RoomMessagePublisher
import kr.mashup.ladder.domain.room.domain.RoomMessageType
import kr.mashup.ladder.domain.room.domain.RoomTopic
import kr.mashup.ladder.playlist.service.PlaylistService
import kr.mashup.ladder.room.dto.request.RoomAcceptPlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomAddPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomSendChatRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest
import org.springframework.stereotype.Service

@Service
class RoomSocketService(
    private val playlistService: PlaylistService,
    private val roomMessagePublisher: RoomMessagePublisher,
) {

    fun sendChat(roomId: Long, request: RoomSendChatRequest) {
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.CHAT, request.toMessage(roomId))
        )
    }

    fun sendEmoji(roomId: Long, request: RoomSendEmojiRequest) {
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.EMOJI, request.toMessage(roomId))
        )
    }

    fun sendPlaylistItemRequest(roomId: Long, request: RoomSendPlaylistItemRequestRequest) {
        val item = playlistService.addItemRequest(request)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAYLIST_ITEM_REQUEST, request.toMessage(roomId, item.id))
        )
    }

    fun acceptPlaylistItemRequest(roomId: Long, memberId: Long, request: RoomAcceptPlaylistItemRequestRequest) {
        playlistService.acceptItemRequest(memberId, request)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAYLIST_ITEM_ADD, request.toMessage(roomId))
        )
    }

    fun addPlaylistItem(roomId: Long, memberId: Long, request: RoomAddPlaylistItemRequest) {
        val item = playlistService.addItem(memberId, request)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAYLIST_ITEM_ADD, request.toMessage(roomId, item.id))
        )
    }

}
