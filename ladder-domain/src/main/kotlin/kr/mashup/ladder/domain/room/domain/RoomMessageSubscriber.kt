package kr.mashup.ladder.domain.room.domain

import com.fasterxml.jackson.core.type.TypeReference
import kr.mashup.ladder.common.util.JsonUtil
import kr.mashup.ladder.domain.mood.domain.MoodSuggestionMessage
import kr.mashup.ladder.domain.room.domain.chat.RoomChatMessage
import kr.mashup.ladder.domain.room.domain.emoji.RoomEmojiMessage
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlayInformationUpdateMessage
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemAddMessage
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemChangeOrderMessage
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemRemoveMessage
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemRequestDeclineMessage
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemRequestMessage
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class RoomMessageSubscriber(
    private val applicationEventPublisher: ApplicationEventPublisher,
) : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        when (JsonUtil.fromByteArray(message.body, RoomMessage::class.java).type) {
            RoomMessageType.CHAT -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomChatMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
            RoomMessageType.EMOJI -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomEmojiMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
            RoomMessageType.PLAYLIST_ITEM_REQUEST -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomPlaylistItemRequestMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
            RoomMessageType.PLAYLIST_ITEM_REQUEST_DECLINE -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomPlaylistItemRequestDeclineMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
            RoomMessageType.PLAYLIST_ITEM_ADD -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomPlaylistItemAddMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
            RoomMessageType.PLAYLIST_ITEM_REMOVE -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomPlaylistItemRemoveMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
            RoomMessageType.PLAYLIST_ITEM_CHANGE_ORDER -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomPlaylistItemChangeOrderMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
            RoomMessageType.PLAY_INFORMATION_UPDATE -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomPlayInformationUpdateMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
            RoomMessageType.MOOD_CHANGE_REQUEST -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<MoodSuggestionMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
        }
    }
}
