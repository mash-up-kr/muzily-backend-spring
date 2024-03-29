package kr.mashup.ladder.room.controller

import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.room.dto.request.RoomAcceptPlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomAddPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomChangeOrderOfPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomDeclinePlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomRemovePlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomSendChatRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomUpdatePlayInformationRequest
import kr.mashup.ladder.room.service.RoomWsService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
class RoomWsController(
    private val roomWsService: RoomWsService,
) {
    @MessageMapping("/v1/rooms/{roomId}/send-chat")
    fun sendChat(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomSendChatRequest,
    ) {
        roomWsService.sendChat(roomId, memberId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/send-emoji")
    fun sendEmoji(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomSendEmojiRequest,
    ) {
        roomWsService.sendEmoji(roomId, memberId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/send-playlist-item-request")
    fun sendPlaylistItemRequest(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomSendPlaylistItemRequestRequest,
    ) {
        roomWsService.sendPlaylistItemRequest(roomId, memberId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/accept-playlist-item-request")
    fun acceptPlaylistItemRequest(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomAcceptPlaylistItemRequestRequest,
    ) {
        roomWsService.acceptPlaylistItemRequest(roomId, memberId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/decline-playlist-item-request")
    fun declinePlaylistItemRequest(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomDeclinePlaylistItemRequestRequest,
    ) {
        roomWsService.declinePlaylistItemRequest(roomId, memberId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/add-playlist-item")
    fun addPlaylistItem(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomAddPlaylistItemRequest,
    ) {
        roomWsService.addPlaylistItem(roomId, memberId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/remove-playlist-item")
    fun removePlaylistItem(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomRemovePlaylistItemRequest,
    ) {
        roomWsService.removePlaylistItem(roomId, memberId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/change-order-playlist-item")
    fun changeOrderPlaylistItem(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomChangeOrderOfPlaylistItemRequest,
    ) {
        roomWsService.changeOrderPlaylistItem(roomId, memberId, request)
    }

    @MessageMapping("/v1/rooms/{roomId}/update-play-information")
    fun updatePlayInformation(
        @DestinationVariable roomId: Long,
        @MemberId memberId: Long,
        @Payload request: RoomUpdatePlayInformationRequest,
    ) {
        roomWsService.updatePlayInformation(roomId, memberId, request)
    }
}
