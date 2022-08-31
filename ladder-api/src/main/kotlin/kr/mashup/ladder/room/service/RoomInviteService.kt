package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.playlist.domain.PlaylistRepository
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.room.dto.response.RoomInfoResponse
import kr.mashup.ladder.room.service.RoomServiceHelper.findRoomByInvitationKey
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomInviteService(
    private val roomRepository: RoomRepository,
    private val playlistRepository: PlaylistRepository,
) {

    @Transactional(readOnly = true)
    fun getByInvitationKey(invitationKey: String, memberId: Long?): RoomInfoResponse {
        val room = findRoomByInvitationKey(roomRepository, invitationKey)
        return RoomInfoResponse.of(
            room = room, memberId = memberId,
            playListItemsCount = playlistRepository.findByRoomId(room.id)?.items?.size ?: 0
        )
    }

    @Transactional
    fun enterRoomByInvitationKey(invitationKey: String, memberId: Long) {
        val room = findRoomByInvitationKey(roomRepository, invitationKey)
        room.addGuest(memberId)
    }

}
