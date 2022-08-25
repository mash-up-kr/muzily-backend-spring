package kr.mashup.ladder.mood.listener

import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.config.context.WsMemberPrincipalContext
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_QUEUE
import kr.mashup.ladder.domain.mood.event.MoodSuggestionReceivedEvent
import kr.mashup.ladder.domain.mood.infra.jpa.MoodSuggestionRepository
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.mood.dto.response.MoodSuggestionReceivedResponse
import kr.mashup.ladder.room.service.RoomServiceHelper
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class MoodSuggestionEventListener(
    private val simpMessagingTemplate: SimpMessagingTemplate,
    private val roomRepository: RoomRepository,
    private val moodSuggestionRepository: MoodSuggestionRepository,
) {

    @EventListener
    fun handleReceivedMoodSuggestion(event: MoodSuggestionReceivedEvent) {
        val room = RoomServiceHelper.findRoomByIdFetchMember(roomRepository, event.roomId)
        val principals = WsMemberPrincipalContext.get(room.getCreator())
        val payload = WsResponse.ok(
            type = WsResponseType.MOOD_REQUEST,
            data = MoodSuggestionReceivedResponse(
                name = event.name,
                totalCounts = moodSuggestionRepository.countByRoomId(roomId = room.id)
            )
        )
        principals.forEach { principal ->
            simpMessagingTemplate.convertAndSendToUser(principal.name, WS_DESTINATION_PREFIX_QUEUE, payload)
        }
    }

}
