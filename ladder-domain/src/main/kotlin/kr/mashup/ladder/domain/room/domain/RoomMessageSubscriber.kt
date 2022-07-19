package kr.mashup.ladder.domain.room.domain

import com.fasterxml.jackson.core.type.TypeReference
import kr.mashup.ladder.domain.room.domain.chat.RoomChatMessage
import kr.mashup.ladder.domain.room.domain.chat.RoomChatMessageReceiveEvent
import kr.mashup.ladder.domain.room.domain.emoji.RoomEmojiMessage
import kr.mashup.ladder.domain.room.domain.emoji.RoomEmojiMessageRecieveEvent
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemAddMessage
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemRequestMessage
import kr.mashup.ladder.domain.room.domain.playlist.RoomPlaylistItemRequestMessageReceiveEvent
import kr.mashup.ladder.domain.util.JsonUtil
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
                applicationEventPublisher.publishEvent(
                    RoomChatMessageReceiveEvent(
                        roomMessage.data.roomId,
                        roomMessage.data.chat
                    )
                )
            }
            RoomMessageType.EMOJI -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomEmojiMessage>>() {})
                applicationEventPublisher.publishEvent(
                    RoomEmojiMessageRecieveEvent(
                        roomMessage.data.roomId,
                        roomMessage.data.emojiType
                    )
                )
            }
            RoomMessageType.PLAYLIST_ITEM_REQUEST -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomPlaylistItemRequestMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
            RoomMessageType.PLAYLIST_ITEM_ADD -> {
                val roomMessage = JsonUtil.fromByteArray(
                    message.body,
                    object : TypeReference<RoomMessage<RoomPlaylistItemAddMessage>>() {})
                applicationEventPublisher.publishEvent(roomMessage.data.toEvent())
            }
            else -> {}
        }
    }
}
