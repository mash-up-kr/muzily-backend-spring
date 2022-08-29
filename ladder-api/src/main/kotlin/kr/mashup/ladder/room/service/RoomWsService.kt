package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.room.domain.RoomMessage
import kr.mashup.ladder.domain.room.domain.RoomMessagePublisher
import kr.mashup.ladder.domain.room.domain.RoomMessageType
import kr.mashup.ladder.domain.room.domain.RoomTopic
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.playlist.service.PlaylistService
import kr.mashup.ladder.room.dto.request.RoomAcceptPlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomAddPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomChangeOrderOfPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomDeclinePlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomRemovePlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomSendChatRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomUpdatePlayInformationRequest
import kr.mashup.ladder.room.service.RoomServiceHelper.validateIsParticipant
import org.springframework.stereotype.Service

@Service
class RoomWsService(
    private val playlistService: PlaylistService,
    private val roomMessagePublisher: RoomMessagePublisher,
    private val roomRepository: RoomRepository,
) {

    fun sendChat(roomId: Long, senderId: Long, request: RoomSendChatRequest) {
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.CHAT, request.toMessage(roomId, senderId))
        )
    }

    fun sendEmoji(roomId: Long, senderId: Long, request: RoomSendEmojiRequest) {
        validateIsParticipant(roomRepository = roomRepository, roomId = roomId, memberId = senderId)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.EMOJI, request.toMessage(roomId, senderId))
        )
    }

    fun sendPlaylistItemRequest(roomId: Long, senderId: Long, request: RoomSendPlaylistItemRequestRequest) {
        val item = playlistService.addItemRequest(request, memberId = senderId)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAYLIST_ITEM_REQUEST, request.toMessage(roomId, senderId, item.playlistItemId))
        )
    }

    fun acceptPlaylistItemRequest(roomId: Long, memberId: Long, request: RoomAcceptPlaylistItemRequestRequest) {
        playlistService.acceptItemRequest(memberId, request)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAYLIST_ITEM_ADD, request.toMessage(roomId))
        )
    }

    fun declinePlaylistItemRequest(roomId: Long, memberId: Long, request: RoomDeclinePlaylistItemRequestRequest) {
        playlistService.declineItemRequest(memberId, request)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAYLIST_ITEM_REQUEST_DECLINE, request.toMessage(roomId))
        )
    }

    fun addPlaylistItem(roomId: Long, memberId: Long, request: RoomAddPlaylistItemRequest) {
        val item = playlistService.addItem(memberId, request)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAYLIST_ITEM_ADD, request.toMessage(roomId, item.playlistItemId))
        )
    }

    fun removePlaylistItem(roomId: Long, memberId: Long, request: RoomRemovePlaylistItemRequest) {
        playlistService.removeItem(memberId, request)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAYLIST_ITEM_REMOVE, request.toMessage(roomId))
        )
    }

    fun changeOrderPlaylistItem(roomId: Long, memberId: Long, request: RoomChangeOrderOfPlaylistItemRequest) {
        val order = playlistService.changeOrder(memberId, request)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAYLIST_ITEM_CHANGE_ORDER, request.toMessage(roomId, order))
        )
    }

    fun updatePlayInformation(roomId: Long, memberId: Long, request: RoomUpdatePlayInformationRequest) {
        playlistService.updatePlayInformation(memberId, request)
        roomMessagePublisher.publish(
            RoomTopic(roomId),
            RoomMessage(RoomMessageType.PLAY_INFORMATION_UPDATE, request.toMessage(roomId))
        )
    }
}
